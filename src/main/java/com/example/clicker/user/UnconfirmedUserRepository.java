package com.example.clicker.user;


import com.example.clicker.user.models.entity.UnconfirmedUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnconfirmedUserRepository extends CrudRepository<UnconfirmedUserEntity, String> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<UnconfirmedUserEntity> findByEmail(String email);

    Optional<UnconfirmedUserEntity> findByUsername(String username);
}
