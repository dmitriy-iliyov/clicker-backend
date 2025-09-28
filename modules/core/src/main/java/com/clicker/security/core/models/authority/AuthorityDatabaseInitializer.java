package com.clicker.security.core.models.authority;

import com.clicker.security.core.models.authority.models.Authority;
import com.clicker.security.core.models.authority.models.AuthorityEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthorityDatabaseInitializer {

    private final AuthorityRepository authorityRepository;

    @PostConstruct
    @Transactional
    public void init() {
        for (Authority authority : Authority.values()) {
            authorityRepository.findByAuthority(authority)
                    .orElseGet(() -> {
                        AuthorityEntity newAuthority = new AuthorityEntity();
                        newAuthority.setAuthority(authority);
                        return authorityRepository.save(newAuthority);
                    });
        }
    }
}

