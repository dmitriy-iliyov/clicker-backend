package com.clicker.models;

import java.util.UUID;

public record ClickerStateDto(
        UUID userId,
        Float probability,
        Integer clicksCount
) {
}
