package com.clicker.wallets.models.dto;

import com.clicker.currency.validation.id.CurrencyId;
import com.clicker.wallets.validation.address.WalletAddress;
import com.clicker.wallets.validation.id.WalletId;
import com.clicker.wallets.validation.unique_address.UniqueWalletAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@WalletAddress
@UniqueWalletAddress
public record WalletUpdateDto(

        @NotNull(message = "Wallet id shouldn't be blank!")
        @Positive(message = "Wallet id shouldn't be negative!")
        @WalletId
        Long id,

        @NotNull(message = "Currency id shouldn't be blank!")
        @CurrencyId
        Long currencyId,

        @NotBlank(message = "Address shouldn't be blank!")
        @Size(max = 100, message = "Wallet length must be less than 100!")
        String address
) { }
