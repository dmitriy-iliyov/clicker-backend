package com.clicker.core.exception;

public class CookieJwtExpired extends RuntimeException {

    public CookieJwtExpired() {
    }

    public CookieJwtExpired(String message) {
        super(message);
    }
}
