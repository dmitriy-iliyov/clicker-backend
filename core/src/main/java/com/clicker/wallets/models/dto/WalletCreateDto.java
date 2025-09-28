package com.clicker.wallets.models.dto;


import com.clicker.currency.validation.id.CurrencyId;
import com.example.clicker.wallets.validation.address.WalletAddress;
import com.example.clicker.wallets.validation.unique_address.UniqueWalletAddress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@WalletAddress
@UniqueWalletAddress
public record WalletCreateDto(

        @NotNull(message = "Currency shouldn't be empty!")
        @Positive(message = "Currency should be positive!")
        @CurrencyId
        Long currencyId,

        @NotBlank(message = "Wallet shouldn't be empty!")
        @Size(max = 100, message = "Wallet length must be less than 100!")
        String address

) { }
