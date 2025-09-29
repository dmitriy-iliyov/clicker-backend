package com.clicker.core.domain.admin;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class AdminEntity {

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    private void beforeCreate() {
        Instant now = Instant.now();
        updatedAt = now;
    }

    @PreUpdate
    private void beforeUpdate() {
        updatedAt = Instant.now();
    }
}
