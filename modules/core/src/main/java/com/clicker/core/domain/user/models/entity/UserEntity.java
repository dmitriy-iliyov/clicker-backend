package com.clicker.core.domain.user.models.entity;

import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;
import com.clicker.core.sgared.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"wallets", "authorities"})
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseAuditEntity {

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
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

    public UserEntity(UUID id, String email, String password, AuthorityEntity authorityEntity) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities.add(authorityEntity);
    }
}
