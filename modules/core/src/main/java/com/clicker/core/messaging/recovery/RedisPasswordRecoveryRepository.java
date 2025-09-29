package com.clicker.core.messaging.recovery;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisPasswordRecoveryRepository implements PasswordRecoveryRepository {

    @Value("${clicker.api.password.recovery.ttl.secs}")
    private Long TOKEN_TTL;

    private final String RECOVERY_TOKEN_KEY_TEMPLATE = "tkn:pass:recov:%s";
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void save(UUID token, String recoveryResource) {
        String key = RECOVERY_TOKEN_KEY_TEMPLATE.formatted(token);
        redisTemplate.opsForValue().set(key, recoveryResource, TOKEN_TTL, TimeUnit.SECONDS);
    }

    @Override
    public Optional<String> findAndDeleteByToken(UUID token) {
        String key = RECOVERY_TOKEN_KEY_TEMPLATE.formatted(token);
        return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(key));
    }
}
