package com.clicker.currency.models.dto;

import com.example.clicker.wallets.models.dto.WalletResponseDto;

import java.util.Set;

public record FullCurrencyResponseDto(
       Long id,
       String code,
       Set<WalletResponseDto> wallets
) { }
