package com.clicker.core.security.core.models.authority;

import com.clicker.core.security.core.models.authority.models.Authority;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;

import java.util.Set;

public interface AuthorityService {
    AuthorityEntity findByAuthority(Authority authority);

    Set<AuthorityEntity> toAuthorityEntityList(Set<Authority> authorities);
}
