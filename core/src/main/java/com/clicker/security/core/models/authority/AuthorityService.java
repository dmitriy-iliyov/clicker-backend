package com.clicker.security.core.models.authority;

import com.clicker.security.core.models.authority.models.Authority;
import com.clicker.security.core.models.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {
    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
