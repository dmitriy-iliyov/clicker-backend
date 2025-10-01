package com.clicker.auth;

import org.springframework.security.core.AuthenticationException;

public class NonExistentTokenException extends AuthenticationException {

    public NonExistentTokenException(String msg) {
        super(msg);
    }
}
