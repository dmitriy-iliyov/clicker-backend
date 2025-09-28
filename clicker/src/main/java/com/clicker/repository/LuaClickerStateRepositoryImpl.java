package com.clicker.repository;

import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LuaClickerStateRepositoryImpl implements LuaClickerStateRepository {

    private final StatefulRedisConnection<String, String> statefulConnection;
    private String SCRIPT_SHA;

    @EventListener(ApplicationReadyEvent.class)
    public void loadScript() {
        try {
            RedisCommands<String, String> redisCommands = statefulConnection.sync();
            SCRIPT_SHA = redisCommands.scriptLoad(getScript().getBytes(StandardCharsets.UTF_8));
        } catch (RedisConnectionFailureException e) {
            log.error("Failed connect to redis, ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error when load script, ", e);
            throw e;
        }
    }

    @Override
    public void updateById(UUID userId, Float probability) {
        try {
            RedisCommands<String, String> redisCommands = statefulConnection.sync();
            if (SCRIPT_SHA != null) {
                redisCommands.evalsha(
                        SCRIPT_SHA,
                        ScriptOutputType.BOOLEAN,
                        userId.toString(),
                        probability.toString()
                );
            } else {
                log.warn("Script sha is null");
                redisCommands.eval(
                        getScript(),
                        ScriptOutputType.BOOLEAN,
                        userId.toString(),
                        probability.toString()
                );
            }
        } catch (RedisConnectionFailureException e) {
            log.error("Failed connect to redis, ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error when updating state, ", e);
            throw e;
        }
    }

    private String getScript() {
        return "";
    }
}
