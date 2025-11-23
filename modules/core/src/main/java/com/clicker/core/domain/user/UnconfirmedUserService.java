package com.clicker.core.domain.user;

import com.clicker.core.domain.user.models.dto.ConfirmedUserDto;
import com.clicker.core.domain.user.models.dto.ShortUserDto;
import com.clicker.core.domain.user.models.dto.UserRegistrationRequest;

import java.util.Optional;

public interface UnconfirmedUserService {

    void save(UserRegistrationRequest userRegistrationRequest);

    boolean existsByEmail(String email);

    Optional<ShortUserDto> systemFindByEmail(String email);

    Optional<ShortUserDto> systemFindByUsername(String username);

    ConfirmedUserDto findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByUsername(String username);
}
