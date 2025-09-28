package com.clicker.service;

import com.clicker.models.ClickerStateDto;

import java.util.UUID;

public interface ClickerService {
    ClickerStateDto loadClickerData(UUID userId);

    float click(UUID userId);

    void saveClickerData(UUID userId);
}
