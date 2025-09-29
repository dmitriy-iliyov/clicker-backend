package com.clicker.exception;

import com.clicker.contracts.exceptions.models.BaseBadException;

public class ClickProcessException extends BaseBadException {

    public ClickProcessException() {
        super();
    }

    public ClickProcessException(String message) {
        super(message);
    }
}
