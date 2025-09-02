package com.example.clicker.currency;

import com.example.clicker.currency.models.CurrencyEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    boolean existsByCode(String code);

    Optional<CurrencyEntity> findByCode(String code);

    @EntityGraph(attributePaths = {"wallets"})
    Optional<CurrencyEntity> findWithWalletsById(Long id);

}

