package com.clicker.core.security.core;

import com.clicker.auth.Token;
import com.clicker.core.security.core.models.token.ActiveTokenService;
import com.clicker.core.security.core.models.token.factory.TokenFactory;
import com.clicker.core.security.core.models.token.serializing.TokenSerializer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
public class CookieJwtSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private final TokenFactory tokenFactory;
    private final TokenSerializer tokenSerializer;
    private final ActiveTokenService activeTokenService;

    @Override
    public void onAuthentication(Authentication authentication,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws SessionAuthenticationException {

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Token token = tokenFactory.generateToken(authentication);
            activeTokenService.activate(token.getId(), Duration.between(Instant.now(), token.getExpiresAt()));
            String jwt = tokenSerializer.serialize(token);
            Cookie cookie = new Cookie("__Host-auth_token", jwt);
            cookie.setPath("/");
            cookie.setDomain(null);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setAttribute("SameSite", "None");
            cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.getExpiresAt()));
            response.addCookie(cookie);
        }
    }
}
