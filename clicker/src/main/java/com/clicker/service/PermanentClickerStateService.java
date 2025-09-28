package com.clicker.service;

import com.clicker.models.ClickerStateDto;

import java.util.UUID;

public interface PermanentClickerStateService {
    void save(ClickerStateDto dto);
    ClickerStateDto findByUserId(UUID userId);
}
