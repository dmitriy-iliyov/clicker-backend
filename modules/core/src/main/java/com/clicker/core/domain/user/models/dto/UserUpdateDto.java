package com.clicker.core.domain.user.models.dto;

import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.security.core.models.authority.models.Authority;

import java.util.List;
import java.util.UUID;

public record UserUpdateDto(
        UUID id,
        String email,
        String password,
        String username,
        List<WalletUpdateDto> wallets,
        String avatarUrl,
        List<Authority> authorities
) { }
