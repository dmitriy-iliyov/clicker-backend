package com.clicker.core.domain.currency.models;

import com.clicker.core.domain.user.models.entity.UserEntity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity {

    @CreatedBy
    protected UserEntity creator;

    @LastModifiedBy
    protected UserEntity updater;
}
