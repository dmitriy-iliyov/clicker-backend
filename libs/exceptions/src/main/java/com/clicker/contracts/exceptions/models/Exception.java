package com.clicker.contracts.exceptions.models;

public abstract class Exception extends RuntimeException {

    public Exception() {}

    public Exception(String message) {
        super(message);
    }
}
