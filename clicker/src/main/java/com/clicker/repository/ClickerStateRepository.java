package com.clicker.repository;

import java.util.Optional;
import java.util.UUID;

public interface ClickerStateRepository<T, ID> {
    T save(T entity);

    boolean existsByUserId(ID id);

    Optional<T> findById(ID id);

    void deleteById(ID id);
}
