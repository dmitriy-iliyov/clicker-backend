package com.clicker.core.domain.wallets.models.dto;

import com.clicker.core.domain.currency.models.CurrencyType;

import java.time.Instant;
import java.util.UUID;

public record FullWalletResponseDto(
        Long id,
        Long currencyId,
        CurrencyType currencyType,
        String address,
        UUID userId,
        Instant createdAt,
        Instant updatedAt
) { }
