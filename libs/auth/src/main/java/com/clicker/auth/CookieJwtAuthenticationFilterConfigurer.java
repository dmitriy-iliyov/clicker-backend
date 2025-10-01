package com.clicker.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;


@RequiredArgsConstructor
public class CookieJwtAuthenticationFilterConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity>{

    private final static String FAILURE_AUTHENTICATION_URL = "/ui/users/login";
    private final TokenUserDetailsService detailsService;
    private final TokenDeserializer tokenDeserializer;

    @Override
    public void init(HttpSecurity builder) {}

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationFilter cookieAuthenticationFilter = new AuthenticationFilter(
                http.getSharedObject(AuthenticationManager.class),
                new CookieJwtAuthenticationConverter(tokenDeserializer)
        );

        cookieAuthenticationFilter.setSuccessHandler(new CookieJwtAuthenticationSuccessHandler());
        cookieAuthenticationFilter.setFailureHandler(new CookieJwtAuthenticationFailureHandler(FAILURE_AUTHENTICATION_URL));

        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(detailsService);
        http.addFilterAfter(cookieAuthenticationFilter, CsrfFilter.class)
                .authenticationProvider(preAuthenticatedAuthenticationProvider);
    }
}
