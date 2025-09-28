package com.clicker.user;

import com.clicker.user.models.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.UUID;

public interface UserFacade {

    void save(UserRegistrationDto userRegistrationDto);

    void confirmByEmail(String email);

    UserResponseDto update(UUID id, UserUpdateDto userUpdateDto);

    void updatePassword(String email, String password);

    SystemUserDto systemFindByEmail(String email);

    SystemUserDto systemFindById(UUID id);

    UserResponseDto findById(UUID id);

    PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password) throws BadCredentialsException;

    SystemUserDto systemFindByUsername(String username);
}
