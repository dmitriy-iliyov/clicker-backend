package com.clicker.repository;

import java.util.UUID;

public interface LuaClickerStateRepository {
    Boolean updateById(UUID userId, Float probability);
}
