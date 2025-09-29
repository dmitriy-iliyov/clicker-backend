package com.clicker.core.domain.user.models.dto;

import com.clicker.core.domain.user.validation.update_user.UserUpdate;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.domain.wallets.validation.wallet_list_update.WalletsUpdate;
import com.clicker.core.security.core.models.authority.models.Authority;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@UserUpdate
public class SystemUserUpdateDto {

    private UUID id;

    @NotBlank(message = "Email shouldn't be empty!")
    @Size(min = 11, max = 50)
    @Email(message = "Email should be valid!")
    private String email;

    @NotBlank(message = "Password can't be empty!")
    @Size(min = 10, max = 40, message = "Password length must be greater than 10 and less than 40!")
    private String password;

    @Size(max = 50, message = "Username length must be less than 50!")
    private String username;

    @Valid
    @WalletsUpdate
    private List<WalletUpdateDto> wallets;

    private String profilePictureUrl;

    private List<Authority> authorities;
}
