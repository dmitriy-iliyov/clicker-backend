package com.clicker.core.exception;

import com.clicker.contracts.exceptions.models.BaseBadException;

public class IncorrectPassword extends BaseBadException {

    private final static String MESSAGE = "Incorrect password!";

    public IncorrectPassword() {
        super(MESSAGE);
    }

    public IncorrectPassword(String message) {
        super(message);
    }
}
