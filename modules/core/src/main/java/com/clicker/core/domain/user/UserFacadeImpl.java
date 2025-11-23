package com.clicker.core.domain.user;

import com.clicker.contracts.exceptions.models.NotFoundException;
import com.clicker.core.domain.user.mapper.UserMapper;
import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.repository.AvatarStorage;
import com.clicker.core.domain.user.validation.UserUpdateValidator;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.domain.wallets.validation.validator.WalletValidator;
import com.clicker.core.sgared.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final UserMapper userMapper;
    private final WalletValidator walletValidator;
    private final AvatarStorage avatarStorage;
    private final UserUpdateValidator userValidator;

    @Override
    public void save(UserRegistrationRequest request) {
        unconfirmedUserService.save(request);
    }

    @Override
    public void confirmByEmail(String email) {
        try {
            userService.confirmUserByEmail(email);
        } catch (NotFoundException e) {
            ConfirmedUserDto confirmedUserDto = unconfirmedUserService.findByEmail(email);
            userService.save(confirmedUserDto);
            unconfirmedUserService.deleteByEmail(email);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public UserResponseDto update(UUID id, UserUpdateRequest request, MultipartFile avatar) {
        FullUserDto existedUser = userValidator.validate(id, request);
        walletValidator.validateWalletOwnership(
                existedUser,
                request.wallets().stream()
                        .map(WalletUpdateDto::getId)
                        .collect(Collectors.toSet())
        );
        String avatarUrl = avatarStorage.save(id, avatar);
        return userService.update(userMapper.toUpdateDto(id, request, avatarUrl, existedUser.authorities()));
    }

    @Override
    public void updatePassword(String email, String password) {
        userService.updatePasswordByEmail(email, password);
    }

    @Override
    public UserResponseDto findById(UUID id) {
        return userService.findWithWalletsById(id);
    }

    @Override
    public PageDto<PublicUserResponseDto> findAll(DefaultUserFilter filter) {
        return userService.findAll(filter, PageRequest.of(filter.getPage(), filter.getSize()));
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
