package com.example.clicker.user.validation.username;

import com.example.clicker.user.UnconfirmedUserService;
import com.example.clicker.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return true;
        }

        if(userService.existsByUsername(username) || unconfirmedUserService.existsByUsername(username)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Username is in use!")
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
