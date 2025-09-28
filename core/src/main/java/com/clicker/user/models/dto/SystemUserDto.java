package com.example.clicker.user.models.dto;

import com.example.clicker.security.core.models.authority.models.Authority;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public record SystemUserDto(
        UUID id,
        String email,
        String password,
        String username,
        List<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
