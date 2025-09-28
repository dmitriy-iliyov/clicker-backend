package com.clicker.security.configs.csrf;

import com.clicker.security.csrf.CsrfTokenMasker;
import com.clicker.security.csrf.CsrfTokenMaskerImpl;
import com.clicker.security.csrf.CsrfTokenSessionAuthenticationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;


@Configuration
public class CsrfToolsConfig {

    @Bean
    public CsrfTokenRepository csrfTokenRepository(){
        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
        csrfTokenRepository.setCookieCustomizer(cookie -> cookie
                .path("/")
                .domain(null)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build());
        return csrfTokenRepository;
    }

    @Bean
    public CsrfTokenSessionAuthenticationStrategy csrfTokenAuthenticationStrategy(CsrfTokenRepository csrfTokenRepository){
        return new CsrfTokenSessionAuthenticationStrategy(csrfTokenRepository);
    }

    @Bean
    public CsrfTokenMasker csrfTokenMasker() {
        return new CsrfTokenMaskerImpl();
    }
}
