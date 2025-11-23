package com.clicker.core.domain.user.validation;

import com.clicker.core.domain.user.models.dto.FullUserDto;
import com.clicker.core.domain.user.models.dto.UserUpdateRequest;

import java.util.UUID;

public interface UserUpdateValidator {
    FullUserDto validate(UUID id, UserUpdateRequest request);
}
