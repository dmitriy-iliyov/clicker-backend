package com.example.clicker.clicker.repository;

import com.example.clicker.clicker.repository.models.RedisProbabilityEntity;

import java.util.Optional;
import java.util.UUID;

public interface InMemoryProbabilityRepository {

    void save(RedisProbabilityEntity probabilityEntity);

    boolean existsByUserId(UUID userId);

    // lua script
    void updateByUserId(RedisProbabilityEntity probabilityEntity);

    Optional<Float> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
