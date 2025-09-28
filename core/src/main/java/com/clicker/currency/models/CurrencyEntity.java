package com.clicker.currency.models;


import com.example.clicker.wallets.models.WalletEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = "wallets")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currencies")
public class CurrencyEntity {

    @Id
    @SequenceGenerator(name = "c_seq", sequenceName = "c_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_seq")
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<WalletEntity> wallets = new HashSet<>();

    // to del in future
    public CurrencyEntity(String code) {
        this.code = code;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PrePersist
    private void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }
}
