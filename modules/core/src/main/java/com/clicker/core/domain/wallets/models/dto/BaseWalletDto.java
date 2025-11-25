package com.clicker.core.domain.wallets.models.dto;

import com.clicker.core.domain.currency.validation.id.CurrencyId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public abstract class BaseWalletDto {
    @NotNull(message = "Currency id shouldn't be blank!")
    @CurrencyId
    protected final Long currencyId;

    @NotBlank(message = "Address shouldn't be blank!")
    @Size(max = 100, message = "Wallet length must be less than 100!")
    protected final String address;

    public BaseWalletDto(Long currencyId, String address) {
        this.currencyId = currencyId;
        this.address = address.trim();
    }
}
