package com.example.clicker.clicker.repository.models;

import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "probabilities")
public class RedisProbabilityEntity extends ProbabilityEntity {
}
