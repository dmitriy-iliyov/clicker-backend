package com.clicker.exception;

import com.clicker.contracts.exceptions.models.BaseBadException;

public class NullUserIdOrProbabilityException extends BaseBadException {

    public NullUserIdOrProbabilityException(String message) {
        super(message);
    }

    public NullUserIdOrProbabilityException() {
        super();
    }
}
