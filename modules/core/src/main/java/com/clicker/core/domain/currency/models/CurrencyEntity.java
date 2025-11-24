package com.clicker.core.domain.currency.models;


import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.shared.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "currencies")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "wallets", callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, unique = true, length = 50)
    private CurrencyType type;

    @OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<WalletEntity> wallets = new HashSet<>();

    public CurrencyEntity(CurrencyType type) {
        this.type = type;
    }
}
