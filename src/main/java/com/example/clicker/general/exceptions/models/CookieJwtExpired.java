package com.example.clicker.general.exceptions.models;

public class CookieJwtExpired extends RuntimeException {

    public CookieJwtExpired() {
    }

    public CookieJwtExpired(String message) {
        super(message);
    }
}
