package com.clicker.core.domain.wallets.models.dto;

import com.clicker.core.domain.currency.models.CurrencyType;

public record WalletResponseDto(
        Long id,
        CurrencyType currencyType,
        String address
) { }
