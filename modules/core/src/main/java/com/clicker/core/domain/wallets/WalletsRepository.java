package com.clicker.core.domain.wallets;

import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Repository
public interface WalletsRepository extends JpaRepository<WalletEntity, Long> {

    @EntityGraph(attributePaths = {"currency"})
    Optional<WalletEntity> findWithCurrencyById(Long id);

    @EntityGraph(attributePaths = {"currency"})
    Set<WalletEntity> findAllWithCurrencyByUserId(UUID id);

    @Query("""
        SELECT new com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto(
            w.id, c.id, c.code, w.address, w.user.id, w.createdAt, w.updatedAt)
        FROM WalletEntity w
        LEFT JOIN w.currency c
        WHERE w.address = :address
    """)
    List<FullWalletResponseDto> findAllFullByAddress(@Param("address") String address);

    @Query("""
        SELECT new com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto(
            w.id, c.id, c.code, w.address, w.user.id, w.createdAt, w.updatedAt)
        FROM WalletEntity w
        LEFT JOIN w.currency c
        WHERE w.id = :id
    """)
    Optional<FullWalletResponseDto> findFullById(@Param("id") Long id);

}
