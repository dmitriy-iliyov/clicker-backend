package com.clicker.general.exceptions.models.validation;

public class BelongWalletValidationException extends ValidationException {

    private final static String MESSAGE = "User hasn't this wallet";

    public BelongWalletValidationException() {
        super(MESSAGE);
    }
}
