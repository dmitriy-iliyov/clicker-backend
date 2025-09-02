package com.example.clicker.general.exceptions.models.confirmation;

public class ConfirmationException extends RuntimeException {

    private static String MESSAGE = "Confirmation exception";

    public ConfirmationException() {
        super(MESSAGE);
    }
}
