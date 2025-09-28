package com.clicker.user.models.dto;

import com.clicker.wallets.models.dto.WalletResponseDto;

import java.util.Set;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String username,
        Set<WalletResponseDto> wallets,
        String profilePictureUrl,
        String createdAt
) { }
