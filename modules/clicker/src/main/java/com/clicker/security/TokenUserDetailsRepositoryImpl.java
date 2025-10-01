package com.clicker.security;

import com.clicker.auth.TokenUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TokenUserDetailsRepositoryImpl implements TokenUserDetailsRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean existsById(UUID id) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("tokens:active:%s".formatted(id)));
    }
}
