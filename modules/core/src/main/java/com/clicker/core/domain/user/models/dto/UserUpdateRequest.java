package com.clicker.core.domain.user.models.dto;

import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.domain.wallets.validation.wallet_list_update.WalletsUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserUpdateRequest(
        @NotBlank(message = "Email shouldn't be empty!")
        @Size(min = 11, max = 50)
        @Email(message = "Email should be valid!")
        String email,

        @NotBlank(message = "Password can't be empty!")
        @Size(min = 10, max = 40, message = "Password length must be greater than 10 and less than 40!")
        String password,

        @Size(max = 50, message = "Username length must be less than 50!")
        String username,

        @Valid
        @WalletsUpdate
        List<WalletUpdateDto> wallets
) { }
