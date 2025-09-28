package com.clicker.repository;

import java.util.UUID;

public interface LuaClickerStateRepository {
    void updateById(UUID userId, Float probability);
}
