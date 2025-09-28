package com.clicker.security.core.models.authority;

import com.clicker.security.core.models.authority.models.Authority;
import com.clicker.security.core.models.authority.models.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

    Optional<AuthorityEntity> findByAuthority(Authority authority);

}
