package com.clicker.core.exception;

import com.clicker.contracts.exceptions.models.ValidationException;

public class BelongWalletValidationException extends ValidationException {

    private final static String MESSAGE = "User hasn't this wallet";

    public BelongWalletValidationException() {
        super(MESSAGE);
    }
}
