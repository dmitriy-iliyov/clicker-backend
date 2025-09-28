package com.clicker.repository;

import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LuaClickerStateRepositoryImpl implements LuaClickerStateRepository {

    @Value("${clicker.api.success-click.probability.ttl.secs}")
    private Long TTL;
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
    public Boolean updateById(UUID userId, Float probability) {

        try {
            RedisCommands<String, String> redisCommands = statefulConnection.sync();
            String key = "clicker:states:tmp:" + userId;
            String [] keys = {key};
            if (SCRIPT_SHA != null && !SCRIPT_SHA.isBlank()) {
                return redisCommands.evalsha(
                        SCRIPT_SHA,
                        ScriptOutputType.BOOLEAN,
                        keys,
                        probability.toString(),
                        TTL.toString()
                );
            } else {
                log.warn("Script SHA is null or empty");
                return redisCommands.eval(
                        getScript(),
                        ScriptOutputType.BOOLEAN,
                        keys,
                        probability.toString(),
                        TTL.toString()
                );
            }
        } catch (RedisConnectionFailureException e) {
            log.error("Failed connect to Redis, ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error when updating clicker state, ", e);
            throw e;
        }
    }

    private static String getScript() {
        return ScriptHolder.SCRIPT;
    }

    private static class ScriptHolder {
        private final static String SCRIPT = loadScript("scripts/click_process.lua");

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
