package com.clicker.core.domain.currency.models;


import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.sgared.InteractorAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "currencies")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "wallets", callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyEntity extends InteractorAuditEntity<UserEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<WalletEntity> wallets = new HashSet<>();

    public CurrencyEntity(String code) {
        this.code = code;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }
}
