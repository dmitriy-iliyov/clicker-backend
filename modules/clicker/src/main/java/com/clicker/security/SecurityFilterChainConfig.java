package com.clicker.security;


import com.clicker.auth.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    @Value("${clicker.api.auth.jwt.secret}")
    private String SECRET;

    @Value("${clicker.ui.login.url}")
    private String LOGIN_URL;

    private final TokenUserDetailsRepository repository;
    private final CorsConfigurationSource configurationSource;

    @Bean
    public TokenDeserializer tokenDeserializer() {
        return new TokenDeserializerImpl(SECRET);
    }

    @Bean
    public TokenUserDetailsService tokenUserDetailsService() {
        return new TokenUserDetailsService(repository);
    }

    @Bean
    public AuthenticationProvider preAuthProvider(TokenUserDetailsService detailsService) {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(detailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenUserDetailsService tokenUserDetailsService,
                                                   TokenDeserializer tokenDeserializer) throws Exception {
        http
                .cors(cors -> cors.configurationSource(configurationSource))
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        new RegexRequestMatcher("/.*", "OPTIONS"),
                        new RegexRequestMatcher("/api/clicker/.*", "GET"),
                        new RegexRequestMatcher("/api/clicker/.*", "POST"))
                )
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/clicker/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                            response.setHeader("Location", LOGIN_URL);
                            response.setContentLength(0);
                        })
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers ->
                        headers
                                .xssProtection(
                                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                                )
                                .contentSecurityPolicy(cps -> cps.policyDirectives("script-src 'self'"))
                );

        http.apply(new CookieJwtAuthenticationFilterConfigurer(tokenUserDetailsService, tokenDeserializer));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/api/clicker")
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**");
    }
}
