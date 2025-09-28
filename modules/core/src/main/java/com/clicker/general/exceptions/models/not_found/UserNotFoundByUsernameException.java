package com.clicker.general.exceptions.models.not_found;

public class UserNotFoundByUsernameException extends NotFoundException {

    private final static String MESSAGE = "User not found";

    public UserNotFoundByUsernameException() {
        super(MESSAGE);
    }
}
