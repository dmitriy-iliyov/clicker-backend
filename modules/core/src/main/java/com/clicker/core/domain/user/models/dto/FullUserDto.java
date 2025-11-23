package com.clicker.core.domain.user.models.dto;

import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.security.core.models.authority.models.Authority;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record FullUserDto(
        UUID id,
        String email,
        String username,
        Set<WalletResponseDto> wallets,
        Set<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
