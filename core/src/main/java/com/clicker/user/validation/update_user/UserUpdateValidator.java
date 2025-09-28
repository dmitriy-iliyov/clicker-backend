package com.clicker.user.validation.update_user;

import com.clicker.general.exceptions.models.not_found.NotFoundException;
import com.clicker.messaging.confirmation.ConfirmationService;
import com.clicker.security.core.models.authority.models.Authority;
import com.clicker.user.UserFacade;
import com.clicker.user.models.dto.SystemUserDto;
import com.clicker.user.models.dto.SystemUserUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdate, SystemUserUpdateDto> {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;


    @Override
    public boolean isValid(SystemUserUpdateDto systemUserUpdateDto, ConstraintValidatorContext constraintValidatorContext) {

        if(userFacade == null) {
            throw new IllegalStateException("UserRepository is not properly initialized.");
        }

        if(systemUserUpdateDto == null) {
            return false;
        }

        boolean hasErrors = false;

        SystemUserDto existedUser;
        try {
             existedUser = userFacade.systemFindByEmail(systemUserUpdateDto.getEmail());
            if(!existedUser.id().equals(systemUserUpdateDto.getId())) {
                hasErrors = true;
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email already in use!")
                        .addPropertyNode("email")
                        .addConstraintViolation();
            } else {
                systemUserUpdateDto.setAuthorities(existedUser.authorities());
            }
        } catch (NotFoundException e) {
            List<Authority> authorities = userFacade.systemFindById(systemUserUpdateDto.getId()).authorities();
            authorities.remove(Authority.ROLE_USER);
            authorities.add(Authority.ROLE_UNCONFIRMED_USER);
            confirmationService.sendConfirmationMessage(systemUserUpdateDto.getEmail());
            systemUserUpdateDto.setAuthorities(authorities);
            return true;
        }

        SystemUserDto existedByUsernameUser;
        try {
            existedByUsernameUser = userFacade.systemFindByUsername(systemUserUpdateDto.getEmail());
            if(!existedByUsernameUser.id().equals(systemUserUpdateDto.getId())) {
                hasErrors = true;
                constraintValidatorContext.buildConstraintViolationWithTemplate("Username already in use!")
                        .addPropertyNode("username")
                        .addConstraintViolation();
            }
        } catch (NotFoundException ignored) {
        }

        return !hasErrors;
    }
}
