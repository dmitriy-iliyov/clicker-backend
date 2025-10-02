package com.clicker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "clicker:states:tmp")
public record TemporaryClickerStateEntity(
        @Id
        String userId,
        Float probability,
        Integer clicksCount,
        @TimeToLive
        Long ttl
) { }
