package com.example.clicker.general.exceptions.models;

public class IllegalInputException extends Exception {

    private final static String MESSAGE = "Illegal input";

    public IllegalInputException() {
        super(MESSAGE);
    }

    public IllegalInputException(String message) {
        super(MESSAGE + ": " + message);
    }
}
