package com.clicker.core.domain.currency.models.dto;

import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;

import java.util.Set;

public record FullCurrencyResponseDto(
       Long id,
       String code,
       Set<WalletResponseDto> wallets
) { }
