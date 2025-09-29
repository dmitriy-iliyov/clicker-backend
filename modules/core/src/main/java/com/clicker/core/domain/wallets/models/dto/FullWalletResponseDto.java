package com.clicker.core.domain.wallets.models.dto;

import java.time.Instant;
import java.util.UUID;

public record FullWalletResponseDto(
        Long id,
        Long currencyId,
        String currencyCode,
        String address,
        UUID userId,
        Instant createdAt,
        Instant updatedAt
) { }
