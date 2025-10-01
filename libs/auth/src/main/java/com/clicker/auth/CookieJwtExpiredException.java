package com.clicker.auth;

import org.springframework.security.core.AuthenticationException;

public class CookieJwtExpiredException extends AuthenticationException {
    public CookieJwtExpiredException(String message) {
        super(message);
    }
}
