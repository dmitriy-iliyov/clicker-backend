package com.clicker.avatar.exceptions;

import com.clicker.general.exceptions.models.not_found.NotFoundException;

public class AvatarNotFoundExceptions extends NotFoundException {

    private final static String MESSAGE = "Avatar not found";

    public AvatarNotFoundExceptions() {
        super(MESSAGE);
    }

    public AvatarNotFoundExceptions(String message) {
        super(message);
    }
}
