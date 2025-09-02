package com.example.clicker.general.exceptions.models.not_found;

public class CurrencyNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Currency not found";

    public CurrencyNotFoundException() {
        super(MESSAGE);
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
