package com.clicker.user;

import com.clicker.general.exceptions.models.not_found.NotFoundException;
import com.example.clicker.user.models.dto.*;
import com.example.clicker.wallets.models.dto.WalletUpdateDto;
import com.example.clicker.wallets.validation.validator.WalletValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final Validator validator;
    private final WalletValidator walletValidator;


    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        unconfirmedUserService.save(userRegistrationDto);
    }

    @Transactional
    @Override
    public void confirmByEmail(String email) {
        try {
            userService.confirmUserByEmail(email);
        } catch (NotFoundException e) {
            SystemUserDto systemUserDto = unconfirmedUserService.systemFindByEmail(email);
            userService.save(systemUserDto);
            unconfirmedUserService.deleteByEmail(email);
        }
    }

    @Override
    public UserResponseDto update(UUID id, UserUpdateDto userUpdateDto) {
        SystemUserUpdateDto systemUserUpdateDto = userService.mapToUpdateDto(userUpdateDto);
        systemUserUpdateDto.setId(id);

        walletValidator.validateWalletOwnership(
                userService.findWithWalletsById(id),
                userUpdateDto.wallets().stream().map(WalletUpdateDto::id).toList()
        );
        Set<ConstraintViolation<SystemUserUpdateDto>> bindingResult = validator.validate(systemUserUpdateDto);
        if(!bindingResult.isEmpty()) {
            throw new ConstraintViolationException(bindingResult);
        }

        return userService.update(systemUserUpdateDto);
    }

    @Override
    public void updatePassword(String email, String password) {
        userService.updatePasswordByEmail(email, password);
    }

    @Override
    public SystemUserDto systemFindById(UUID id) {
        return userService.systemFindById(id);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email) throws NotFoundException {
        try {
            return userService.systemFindByEmail(email);
        } catch (NotFoundException e) {
            return unconfirmedUserService.systemFindByEmail(email);
        }
    }

    @Override
    public SystemUserDto systemFindByUsername(String username) {
        try {
            return userService.systemFindByUsername(username);
        } catch (NotFoundException e) {
            return unconfirmedUserService.systemFindByUsername(username);
        }
    }

    @Override
    public UserResponseDto findById(UUID id) {
        return userService.findWithWalletsById(id);
    }

    @Override
    public PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest) {
        return userService.findAll(filter, pageRequest);
    }

    @Override
    public void deleteById(UUID id) {
        userService.deleteById(id);
    }

    @Override
    public void deleteByPassword(UUID id, String password) throws BadCredentialsException {
        userService.deleteByPassword(id, password);
    }
}
