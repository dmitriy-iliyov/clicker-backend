package com.clicker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@RedisHash(value = "clicker:states:tmp")
public record TemporaryClickerStateEntity(
        @Id
        UUID userId,
        Float probability,
        Integer clicksCount,
        @TimeToLive
        Long ttl
) { }
