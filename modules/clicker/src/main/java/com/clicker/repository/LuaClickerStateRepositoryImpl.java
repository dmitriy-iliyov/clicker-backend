package com.clicker.repository;

import io.lettuce.core.RedisException;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LuaClickerStateRepositoryImpl implements LuaClickerStateRepository {

    @Value("${clicker.api.success-click.probability.ttl.secs}")
    private long TTL;
    private final StatefulRedisConnection<String, String> statefulConnection;
    private String SCRIPT_SHA;
    private final String SCRIPT_TEMPLATE = "clicker:states:tmp:%s";

    @PostConstruct
    public void loadScript() {
        try {
            RedisCommands<String, String> redisCommands = statefulConnection.sync();
            SCRIPT_SHA = redisCommands.scriptLoad(getScript().getBytes(StandardCharsets.UTF_8));
        } catch (RedisConnectionFailureException e) {
            log.error("Failed connect to redis, ", e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Error when load script, ", e);
            throw e;
        }
    }

    @Override
    public Boolean updateById(UUID userId, Float probability) {
        try {
            String [] keys = {SCRIPT_TEMPLATE.formatted(userId)};
            return statefulConnection.sync().evalsha(
                    SCRIPT_SHA, ScriptOutputType.BOOLEAN, keys, probability.toString(), String.valueOf(TTL)
            );
        } catch (RedisConnectionFailureException e) {
            log.error("Failed connect to Redis, ", e);
            throw e;
        } catch (RedisException e ) {
            log.error("Redis exception: ", e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Error when updating clicker state, ", e);
            throw e;
        }
    }

    private static String getScript() {
        return ScriptHolder.SCRIPT;
    }

    private static class ScriptHolder {
        private static final String SCRIPT = loadScript("scripts/click_process.lua");

        private static String loadScript(String filepath) {
            try (InputStream is = LuaClickerStateRepositoryImpl.class.getClassLoader()
                    .getResourceAsStream(filepath)) {
                if (is == null) {
                    throw new FileNotFoundException(filepath + " not found in resources");
                }
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("Error reading Lua script file: {}", filepath, e);
                throw new RuntimeException(e);
            }
        }
    }
}
