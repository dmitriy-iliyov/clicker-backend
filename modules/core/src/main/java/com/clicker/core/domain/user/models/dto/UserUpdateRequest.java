package com.clicker.core.domain.user.models.dto;

import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;

import java.util.List;

public record UserUpdateDto(
        String email,
        String password,
        String username,
        List<WalletUpdateDto> wallets,
        String profilePictureUrl
) { }
