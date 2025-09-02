package com.example.clicker.general.exceptions.models.not_found;

public class UserNotFoundException extends NotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
