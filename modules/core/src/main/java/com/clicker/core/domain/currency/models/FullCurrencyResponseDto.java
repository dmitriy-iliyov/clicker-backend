package com.clicker.core.domain.currency.models;

import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;

import java.util.Set;

public record FullCurrencyResponseDto(
       Long id,
       CurrencyType currencyType,
       Set<WalletResponseDto> wallets
) { }
