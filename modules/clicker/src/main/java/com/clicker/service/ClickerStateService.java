package com.clicker.service;

import com.clicker.models.ClickerStateDto;

import java.util.UUID;

public interface ClickerStateService {
    void save(UUID userId);

    void update(UUID userId, Float probability);

    ClickerStateDto findByUserId(UUID userId);

    Float findProbabilityByUserId(UUID userId);
}
