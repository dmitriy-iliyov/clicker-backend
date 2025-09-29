package com.clicker.contracts.exceptions.models;

public class BaseInternalServerException extends Exception {

    public BaseInternalServerException() {
        super();
    }

    public BaseInternalServerException(String message) {
        super(message);
    }
}
