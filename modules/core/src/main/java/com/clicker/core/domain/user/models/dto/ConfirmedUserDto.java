package com.clicker.core.domain.user.models.dto;

import com.clicker.core.security.core.models.authority.models.Authority;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;


public record ConfirmedUserDto(
        UUID id,
        String email,
        String password,
        String username,
        Set<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
