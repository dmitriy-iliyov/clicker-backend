package com.clicker.core.domain.avatar.exceptions;

import com.clicker.contracts.exceptions.models.NotFoundException;

public class AvatarNotFoundExceptions extends NotFoundException {

    private final static String MESSAGE = "Avatar not found";

    public AvatarNotFoundExceptions() {
        super(MESSAGE);
    }

    public AvatarNotFoundExceptions(String message) {
        super(message);
    }
}
