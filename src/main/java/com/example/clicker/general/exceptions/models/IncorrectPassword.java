package com.example.clicker.general.exceptions.models;

public class IncorrectPassword extends IllegalInputException {

    private final static String MESSAGE = "Incorrect password!";

    public IncorrectPassword() {
        super(MESSAGE);
    }

    public IncorrectPassword(String message) {
        super(message);
    }
}
