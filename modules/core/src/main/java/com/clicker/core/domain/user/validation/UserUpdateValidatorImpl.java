package com.clicker.core.domain.user.validation;

import com.clicker.core.domain.user.UnconfirmedUserService;
import com.clicker.core.domain.user.UserService;
import com.clicker.core.domain.user.models.dto.EmailConfirmationEvent;
import com.clicker.core.domain.user.models.dto.FullUserDto;
import com.clicker.core.domain.user.models.dto.ShortUserDto;
import com.clicker.core.domain.user.models.dto.UserUpdateRequest;
import com.clicker.core.security.core.models.authority.models.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserUpdateValidatorImpl implements UserUpdateValidator {

    private final UserService userService;
    private final UnconfirmedUserService unconfirmedUserService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public FullUserDto validate(UUID id, UserUpdateRequest request) {
        FullUserDto existedUser = userService.findFullById(id);
        if (!existedUser.email().equals(request.email())) {
            compositeFindByEmail(request.email())
                    .ifPresentOrElse(
                            existed -> {
                                if(!existed.id().equals(id)) {
                                    throw new RuntimeException("Email already in use!");
                                }
                            },
                            () -> {
                                existedUser.authorities().remove(Authority.ROLE_USER);
                                existedUser.authorities().add(Authority.ROLE_UNCONFIRMED_USER);
                                eventPublisher.publishEvent(new EmailConfirmationEvent(request.email()));
                            }
                    );
        }
        if (!existedUser.username().equals(request.username())) {
            compositeFindByUsername(request.username())
                    .ifPresent(dto -> {
                        if(!dto.id().equals(id)) {
                            throw new RuntimeException("Username already in use!");
                        }
                    });
        }
        return existedUser;
    }

    private Optional<ShortUserDto> compositeFindByEmail(String email) {
        Optional<ShortUserDto> dto = userService.findShortByEmail(email);
        if (dto.isEmpty()) {
            return unconfirmedUserService.systemFindByEmail(email);
        }
        return dto;
    }

    private Optional<ShortUserDto> compositeFindByUsername(String username) {
        Optional<ShortUserDto> dto = userService.findShortByUsername(username);
        if (dto.isEmpty()) {
            return unconfirmedUserService.systemFindByUsername(username);
        }
        return dto;
    }
}
