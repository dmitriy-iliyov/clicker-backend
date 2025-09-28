package com.clicker.general.exceptions.models.validation;

import com.clicker.general.exceptions.models.Exception;

public class ValidationException extends Exception {

    private final static String MESSAGE = "Entered data is invalid";

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
        super(MESSAGE);
    }
}
