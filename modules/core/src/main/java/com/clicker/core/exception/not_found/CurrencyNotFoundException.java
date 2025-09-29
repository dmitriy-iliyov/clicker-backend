package com.clicker.core.exception.not_found;

import com.clicker.contracts.exceptions.models.NotFoundException;

public class CurrencyNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Currency not found";

    public CurrencyNotFoundException() {
        super(MESSAGE);
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
