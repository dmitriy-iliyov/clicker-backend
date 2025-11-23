package com.clicker.core.domain.user;

import com.clicker.core.PageDto;
import com.clicker.core.domain.user.models.dto.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserFacade {

    void save(UserRegistrationRequest userRegistrationRequest);

    void confirmByEmail(String email);

    UserResponseDto update(UUID id, UserUpdateRequest userUpdateRequest, MultipartFile avatar);

    void updatePassword(String email, String password);

    UserResponseDto findById(UUID id);

    PageDto<PublicUserResponseDto> findAll(DefaultUserFilter filter);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password) throws BadCredentialsException;
}
