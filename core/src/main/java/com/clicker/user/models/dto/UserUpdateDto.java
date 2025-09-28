package com.clicker.user.models.dto;

import com.clicker.wallets.models.dto.WalletUpdateDto;

import java.util.List;

public record UserUpdateDto(
        String email,
        String password,
        String username,
        List<WalletUpdateDto> wallets,
        String profilePictureUrl
) { }
