package com.clicker.repository;

import com.clicker.models.TemporaryClickerStateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
public interface InMemoryClickerStateRepository extends KeyValueRepository<TemporaryClickerStateEntity, UUID>,
                                                        ClickerStateRepository<TemporaryClickerStateEntity, UUID>,
                                                        LuaClickerStateRepository {
}
