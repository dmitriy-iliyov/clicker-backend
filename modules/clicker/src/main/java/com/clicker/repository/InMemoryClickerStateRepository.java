package com.clicker.repository;

import com.clicker.models.TemporaryClickerStateEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InMemoryClickerStateRepository extends KeyValueRepository<TemporaryClickerStateEntity, String>,
                                                        ClickerStateRepository<TemporaryClickerStateEntity, String>,
                                                        LuaClickerStateRepository {
}
