package com.clicker.service;

import com.clicker.models.ClickerStateDto;

import java.util.UUID;

public interface ClickerStateService {
    void save(UUID userId);

    void update(UUID userId, float probability, int clickCount);

    ClickerStateDto findByUserId(UUID userId);
}
