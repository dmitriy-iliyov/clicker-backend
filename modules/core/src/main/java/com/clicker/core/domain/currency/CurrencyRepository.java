package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.CurrencyType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    boolean existsByType(CurrencyType code);

    Optional<CurrencyEntity> findByType(CurrencyType code);

    @EntityGraph(attributePaths = {"wallets"})
    Optional<CurrencyEntity> findWithWalletsById(Long id);

}

