package com.clicker.contracts.exceptions.models;

public class ValidationException extends BaseBadException {

    private final static String MESSAGE = "Entered data is invalid";

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super(MESSAGE);
    }
}
