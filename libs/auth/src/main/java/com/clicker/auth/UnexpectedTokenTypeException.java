package com.clicker.auth;

import org.springframework.security.core.AuthenticationException;

public class UnexpectedTokenTypeException extends AuthenticationException {
    public UnexpectedTokenTypeException(String msg) {
        super(msg);
    }
}
