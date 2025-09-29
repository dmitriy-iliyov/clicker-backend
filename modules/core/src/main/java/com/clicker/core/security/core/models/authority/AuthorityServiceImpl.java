package com.clicker.core.security.core.models.authority;

import com.clicker.core.exception.not_found.AuthorityNotFoundException;
import com.clicker.core.security.core.models.authority.models.Authority;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    @Transactional(readOnly = true)
    public AuthorityEntity findByAuthority(Authority authority) {
        return authorityRepository.findByAuthority(authority).orElseThrow(AuthorityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorityEntity> toAuthorityEntityList(List<Authority> authorities) {
        List<AuthorityEntity> authorityEntities = new ArrayList<>();
        for (Authority authority: authorities) {
            authorityEntities.add(authorityRepository.findByAuthority(authority).orElseThrow(
                    AuthorityNotFoundException::new)
            );
        }
        return authorityEntities;
    }
}
