package com.clicker.user.validation.email;

import com.clicker.user.UnconfirmedUserService;
import com.clicker.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(userService == null || unconfirmedUserService == null) {
            throw new IllegalStateException("UserRepository is not properly initialized.");
        }

        if (userService.existsByEmail(email) || unconfirmedUserService.existsByEmail(email)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Email is in use!")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
