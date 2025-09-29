package com.clicker.core.exception.not_found;

import com.clicker.contracts.exceptions.models.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
