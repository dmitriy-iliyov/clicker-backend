package com.clicker.core.domain.user.models.entity;

import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.*;


@Data
@ToString(exclude = {"wallets", "authorities"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<WalletEntity> wallets = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<AuthorityEntity> authorities = new ArrayList<>();

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public UserEntity(UUID id, String email, String password, AuthorityEntity authorityEntity) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities.add(authorityEntity);
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        isExpired = false;
        isLocked = false;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
