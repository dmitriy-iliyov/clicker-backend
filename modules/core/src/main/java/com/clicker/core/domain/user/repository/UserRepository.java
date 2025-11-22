package com.clicker.core.domain.user.repository;

import com.clicker.core.domain.user.models.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityById(UUID id);

    @EntityGraph(attributePaths = {"wallets"})
    Optional<UserEntity> findWithWalletsById(UUID id);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityByEmail(String email);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<UserEntity> findWithAuthorityByUsername(String username);
}
