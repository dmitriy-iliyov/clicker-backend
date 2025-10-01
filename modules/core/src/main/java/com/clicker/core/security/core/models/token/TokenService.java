package com.clicker.core.security.core.models.token;

import com.clicker.auth.TokenUserDetailsRepository;
import com.clicker.core.security.core.models.token.models.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService implements ActiveTokenService, DeactivateTokenService, TokenUserDetailsRepository {

    private final String KEY_TEMPLATE = "tokens:active:%s";
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void activate(UUID id, Duration ttl) {
        redisTemplate.opsForValue().set(KEY_TEMPLATE.formatted(id), "", ttl);
    }

    @Override
    public boolean existsById(UUID id) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(KEY_TEMPLATE.formatted(id)));
    }

    @Override
    public void deactivate(TokenEntity token) {
        redisTemplate.opsForValue().getAndDelete(KEY_TEMPLATE.formatted(token.getId()));
    }
}
