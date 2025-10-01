package com.clicker.core.security.configs.authentication;

import com.clicker.auth.CookieJwtAuthenticationFilterConfigurer;
import com.clicker.auth.TokenDeserializer;
import com.clicker.auth.TokenUserDetailsRepository;
import com.clicker.auth.TokenUserDetailsService;
import com.clicker.core.domain.user.UserService;
import com.clicker.core.security.core.CookieJwtSessionAuthenticationStrategy;
import com.clicker.core.security.core.models.token.ActiveTokenService;
import com.clicker.core.security.core.models.token.factory.TokenFactory;
import com.clicker.core.security.core.models.token.serializing.TokenSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@Log4j2
@RequiredArgsConstructor
public class AuthenticationToolsConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenUserDetailsRepository detailsRepository;
    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;
    private final TokenDeserializer tokenDeserializer;
    private final ActiveTokenService activeTokenService;

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        log.info("Successfully configured ProviderManager");
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public CookieJwtSessionAuthenticationStrategy cookieJwtSessionAuthenticationStrategy(){
        return new CookieJwtSessionAuthenticationStrategy(tokenFactory, tokenSerializer, activeTokenService);
    }

    @Bean
    public CookieJwtAuthenticationFilterConfigurer cookieJwtAuthenticationFilterConfigurer(){
        return new CookieJwtAuthenticationFilterConfigurer(new TokenUserDetailsService(detailsRepository), tokenDeserializer);
    }
}
