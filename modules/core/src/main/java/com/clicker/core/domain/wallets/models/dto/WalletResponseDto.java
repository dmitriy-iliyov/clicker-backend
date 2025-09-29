package com.clicker.core.domain.wallets.models.dto;

public record WalletResponseDto(
        Long id,
        String currencyCode,
        String address
) { }
