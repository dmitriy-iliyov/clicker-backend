package com.clicker.core.shared;

import com.clicker.auth.TokenUserDetails;
import com.clicker.core.domain.user.models.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<UserEntity> {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(authentication -> (TokenUserDetails) authentication.getPrincipal())
                .map(principal -> entityManager.getReference(UserEntity.class, principal.getUserId()));
    }
}
