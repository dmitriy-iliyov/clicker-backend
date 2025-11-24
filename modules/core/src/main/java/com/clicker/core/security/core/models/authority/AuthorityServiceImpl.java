package com.clicker.core.security.core.models.authority;

import com.clicker.core.exception.not_found.AuthorityNotFoundException;
import com.clicker.core.security.core.models.authority.models.Authority;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public AuthorityEntity findByAuthority(Authority authority) {
        return authorityRepository.findByAuthority(authority).orElseThrow(AuthorityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<AuthorityEntity> toAuthorityEntitySet(Set<Authority> authorities) {
        Set<AuthorityEntity> authorityEntities = new HashSet<>();
        for (Authority authority: authorities) {
            authorityEntities.add(authorityRepository.findByAuthority(authority).orElseThrow(
                    AuthorityNotFoundException::new)
            );
        }
        return authorityEntities;
    }
}
