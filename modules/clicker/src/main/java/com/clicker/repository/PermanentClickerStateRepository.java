package com.clicker.repository;

import com.clicker.models.ClickerStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermanentClickerStateRepository extends JpaRepository<ClickerStateEntity, UUID>,
                                                         ClickerStateRepository<ClickerStateEntity, UUID> {
}
