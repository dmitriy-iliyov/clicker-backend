package com.example.clicker.wallets.models.dto;

public record WalletResponseDto(
        Long id,
        String currencyCode,
        String address
) { }
