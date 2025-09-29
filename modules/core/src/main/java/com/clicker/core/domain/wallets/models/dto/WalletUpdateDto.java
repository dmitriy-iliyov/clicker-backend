package com.clicker.core.domain.wallets.models.dto;

import com.clicker.core.domain.currency.validation.id.CurrencyId;
import com.clicker.core.domain.wallets.validation.address.WalletAddress;
import com.clicker.core.domain.wallets.validation.unique_address.UniqueWalletAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@WalletAddress
@UniqueWalletAddress
@Data
public class WalletUpdateDto {
        @JsonIgnore
        private Long id;

        @NotNull(message = "Currency id shouldn't be blank!")
        @CurrencyId
        private Long currencyId;

        @NotBlank(message = "Address shouldn't be blank!")
        @Size(max = 100, message = "Wallet length must be less than 100!")
        private String address;
}
