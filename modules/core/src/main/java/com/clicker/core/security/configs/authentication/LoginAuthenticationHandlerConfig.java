package com.clicker.core.security.configs.authentication;

import com.clicker.core.security.JwtNCsrfSessionAuthenticationStrategy;
import com.clicker.core.security.configs.UrlConfig;
import com.clicker.core.security.core.CookieJwtSessionAuthenticationStrategy;
import com.clicker.core.security.core.handlers.login.LoginAuthenticationSuccessHandler;
import com.clicker.core.security.csrf.CsrfTokenSessionAuthenticationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class LoginAuthenticationHandlerConfig {

    private final CookieJwtSessionAuthenticationStrategy cookieJwtAuthenticationStrategy;
    private final CsrfTokenSessionAuthenticationStrategy csrfAuthenticationStrategy;

    @Bean
    public LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler(){
        LoginAuthenticationSuccessHandler loginSuccessHandler = new LoginAuthenticationSuccessHandler(
                        new JwtNCsrfSessionAuthenticationStrategy(cookieJwtAuthenticationStrategy, csrfAuthenticationStrategy),
                        UrlConfig.PROFILE_PAGE_URL, UrlConfig.ADMIN_HOME_PAGE_URL
        );
        loginSuccessHandler.setDefaultTargetUrl(UrlConfig.CLICKER_PAGE_URL);
        loginSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        return loginSuccessHandler;
    }
}
