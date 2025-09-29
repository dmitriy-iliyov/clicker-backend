package com.clicker.core.exception;

public class ConfirmationException extends RuntimeException {

    private static String MESSAGE = "Confirmation exception";

    public ConfirmationException() {
        super(MESSAGE);
    }
}
