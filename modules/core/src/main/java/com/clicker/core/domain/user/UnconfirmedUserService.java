package com.clicker.core.domain.user;

import com.clicker.core.domain.user.models.dto.UserDto;
import com.clicker.core.domain.user.models.dto.UserRegistrationRequest;

import java.util.Optional;

public interface UnconfirmedUserService {

    void save(UserRegistrationRequest userRegistrationRequest);

    boolean existsByEmail(String email);

    Optional<UserDto> systemFindByEmail(String email);

    UserDto findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByUsername(String username);

    Optional<UserDto> systemFindByUsername(String username);
}
