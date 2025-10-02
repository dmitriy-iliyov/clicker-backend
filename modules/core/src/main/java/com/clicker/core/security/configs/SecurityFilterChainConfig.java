package com.clicker.core.security.configs;


import com.clicker.auth.CookieJwtAuthenticationFilterConfigurer;
import com.clicker.core.security.core.handlers.login.LoginAuthenticationFailureHandler;
import com.clicker.core.security.core.handlers.login.LoginAuthenticationSuccessHandler;
import com.clicker.core.security.core.handlers.logout.DeactivatingJwtLogoutHandler;
import com.clicker.core.security.core.handlers.logout.LogoutSuccessHandlerImpl;
import com.clicker.core.security.core.models.token.DeactivateTokenService;
import com.clicker.core.security.xss.XssFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationConfigurer;
    private final CsrfTokenRepository csrfTokenRepository;
    private final DeactivateTokenService deactivateTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CookieClearingLogoutHandler cookieClearingLogoutHandler =
                new CookieClearingLogoutHandler("__Host-auth_token", "XSRF-TOKEN");
        http
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl(UrlConfig.USER_LOGIN_URL)
                        .loginPage(UrlConfig.USER_LOGIN_PAGE_URL)
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(loginAuthenticationSuccessHandler)
                        .failureHandler(new LoginAuthenticationFailureHandler(UrlConfig.USER_LOGIN_PAGE_URL))
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(UrlConfig.USER_LOGOUT_URL)
                        .addLogoutHandler(cookieClearingLogoutHandler)
                        .addLogoutHandler(new DeactivatingJwtLogoutHandler(deactivateTokenService))
                        .logoutSuccessHandler(new LogoutSuccessHandlerImpl(UrlConfig.HOME_PAGE_URL))
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            cookieClearingLogoutHandler.logout(request, response,
                                    SecurityContextHolder.getContext().getAuthentication());
                            response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                            response.setHeader("Location", UrlConfig.USER_LOGIN_PAGE_URL);
                            response.setContentLength(0);
                        })
                )
                .addFilterAfter(new XssFilter(), CsrfFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(UrlConfig.NO_AUTH_PERMITTED_URL_LIST).permitAll()
                        .requestMatchers(HttpMethod.POST, UrlConfig.USER_REGISTRATION_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository)
                        .ignoringRequestMatchers(UrlConfig.NO_CSRF_PERMITTED_URL_LIST)
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(((authentication, request, response) -> {}))
                )
                .headers(headers ->
                        headers.xssProtection(
                                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        ).contentSecurityPolicy(
                                cps -> cps.policyDirectives("script-src 'self'")
                        ));

        http.apply(cookieJwtAuthenticationConfigurer);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/static/**");
    }
}