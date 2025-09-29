package com.clicker.repository;

import java.util.Optional;

public interface ClickerStateRepository<T, ID> {
    T save(T entity);

    boolean existsByUserId(ID id);

    Optional<T> findById(ID id);

    void deleteById(ID id);
}
