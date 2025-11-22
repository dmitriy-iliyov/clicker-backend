package com.clicker.core.domain.user;

import com.clicker.contracts.exceptions.models.NotFoundException;
import com.clicker.core.domain.user.mapper.UserMapper;
import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.repository.AvatarStorage;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.domain.wallets.validation.validator.WalletValidator;
import com.clicker.core.exception.not_found.UserNotFoundByIdException;
import com.clicker.core.messaging.confirmation.ConfirmationService;
import com.clicker.core.security.core.models.authority.models.Authority;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final UserMapper mapper;
    private final WalletValidator walletValidator;
    private final AvatarStorage avatarStorage;
    private final ConfirmationService confirmationService;

    @Override
    public void save(UserRegistrationRequest request) {
        unconfirmedUserService.save(request);
    }

    @Override
    public void confirmByEmail(String email) {
        try {
            userService.confirmUserByEmail(email);
        } catch (NotFoundException e) {
            UserDto userDto = unconfirmedUserService.findByEmail(email);
            userService.save(userDto);
            unconfirmedUserService.deleteByEmail(email);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public UserResponseDto update(UUID id, UserUpdateRequest request, MultipartFile avatar) {
        List<Authority> authorities = new ArrayList<>();
        validate(id, authorities, request);
        walletValidator.validateWalletOwnership(
                userService.findWithWalletsById(id),
                request.wallets().stream().map(WalletUpdateDto::getId).toList()
        );
        String avatarUrl = avatarStorage.save(id, avatar);
        return userService.update(mapper.toUpdateDto(id, request, avatarUrl, authorities));
    }

    private void validate(UUID id, List<Authority> authorities, UserUpdateRequest request) {
        compositeFindByEmail(request.email())
                .ifPresentOrElse(
                        existed -> {
                            if(!existed.id().equals(id)) {
                                throw new RuntimeException("Email already in use!");
                            } else {
                                authorities.addAll(existed.authorities());
                            }
                        },
                        () -> {
                            authorities.addAll(userService.systemFindById(id)
                                    .orElseThrow(UserNotFoundByIdException::new)
                                    .authorities());
                            authorities.remove(Authority.ROLE_USER);
                            authorities.add(Authority.ROLE_UNCONFIRMED_USER);
                            confirmationService.sendConfirmationMessage(request.email());
                        }
                );
        compositeFindByUsername(request.username())
                .ifPresent(dto -> {
                    if(!dto.id().equals(id)) {
                        throw new RuntimeException("Username already in use!");
                    }
                });
    }

    private Optional<UserDto> compositeFindByEmail(String email) {
        Optional<UserDto> dto = userService.systemFindByEmail(email);
        if (dto.isEmpty()) {
            return unconfirmedUserService.systemFindByEmail(email);
        }
        return dto;
    }

    private Optional<UserDto> compositeFindByUsername(String username) {
        Optional<UserDto> dto = userService.systemFindByUsername(username);
        if (dto.isEmpty()) {
            return unconfirmedUserService.systemFindByUsername(username);
        }
        return dto;
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
