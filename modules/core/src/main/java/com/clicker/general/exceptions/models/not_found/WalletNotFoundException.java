package com.clicker.general.exceptions.models.not_found;

public class WalletNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Wallet not found";

    public WalletNotFoundException() {
        super(MESSAGE);
    }
}
