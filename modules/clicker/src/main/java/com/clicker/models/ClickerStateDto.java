package com.clicker.models;

import java.util.UUID;

public record ClickerStateDto(
        UUID userId,
        Float probability,
        Integer clicksCount
) {
    public String toJson() {
        return "{\"userId\": \"%s\",\"clicksCount\": %d,\"probability\": %.2f}".formatted(userId, clicksCount, probability);
    }
}
