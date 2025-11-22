package com.clicker.core.domain.user.models.dto;

import com.clicker.core.security.core.models.authority.models.Authority;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public record UserDto(
        UUID id,
        String email,
        String password,
        String username,
        List<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
