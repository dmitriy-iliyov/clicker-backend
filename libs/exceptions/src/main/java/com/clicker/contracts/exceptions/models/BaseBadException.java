package com.clicker.contracts.exceptions.models;

public class BaseBadException extends Exception {

    private final static String MESSAGE = "Illegal input";

    public BaseBadException() {
        super(MESSAGE);
    }

    public BaseBadException(String message) {
        super(MESSAGE + ": " + message);
    }
}
