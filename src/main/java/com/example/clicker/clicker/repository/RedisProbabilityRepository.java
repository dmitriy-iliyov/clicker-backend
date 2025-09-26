package com.example.clicker.clicker.repository;

import com.example.clicker.clicker.repository.models.RedisProbabilityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisProbabilityRepository implements InMemoryProbabilityRepository {

    @Value("${clicker.api.clicker.cached.probability.ttl.secs}")
    private Long USER_PROBABILITY_TTL;

    private final String USER_PROBABILITY_KEY_TEMPLATE = "user:prob:%s";
    private final RedisTemplate<String, Float> redisTemplate;

    @Override
    public void save(RedisProbabilityEntity probabilityEntity) {
        redisTemplate.opsForValue().set(
                formatRedisKey(probabilityEntity.getUserId()),
                probabilityEntity.getProbability()
        );
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(formatRedisKey(userId)));
    }

    // todo lua script

    @Override
    public void updateByUserId(RedisProbabilityEntity probabilityEntity) {
        redisTemplate.opsForValue().setIfPresent(
                formatRedisKey(probabilityEntity.getUserId()),
                probabilityEntity.getProbability(),
                USER_PROBABILITY_TTL,
                TimeUnit.SECONDS
        );
    }

    @Override
    public Optional<Float> findByUserId(UUID userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(formatRedisKey(userId)));
    }

    @Override
    public void deleteByUserId(UUID userId) {
        redisTemplate.opsForValue().getAndDelete(formatRedisKey(userId));
    }

    private String formatRedisKey(UUID userId) {
        return USER_PROBABILITY_KEY_TEMPLATE.formatted(userId);
    }
}
