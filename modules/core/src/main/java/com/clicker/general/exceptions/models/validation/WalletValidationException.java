package com.clicker.general.exceptions.models.validation;

public class WalletValidationException extends ValidationException {

    private final static String MESSAGE = "Unexpected wallet validation exception";

    public WalletValidationException(String message) {
        super(message);
    }

    public WalletValidationException() {
        super(MESSAGE);
    }
}
