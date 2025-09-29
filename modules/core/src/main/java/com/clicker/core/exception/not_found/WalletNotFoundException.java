package com.clicker.core.exception.not_found;

import com.clicker.contracts.exceptions.models.NotFoundException;

public class WalletNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Wallet not found";

    public WalletNotFoundException() {
        super(MESSAGE);
    }
}
