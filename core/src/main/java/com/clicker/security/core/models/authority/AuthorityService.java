package com.example.clicker.security.core.models.authority;

import com.example.clicker.security.core.models.authority.models.Authority;
import com.example.clicker.security.core.models.authority.models.AuthorityEntity;

import java.util.List;

public interface AuthorityService {
    AuthorityEntity findByAuthority(Authority authority);

    List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities);
}
