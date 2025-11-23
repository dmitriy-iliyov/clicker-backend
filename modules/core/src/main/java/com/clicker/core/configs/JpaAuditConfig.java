package com.clicker.core.configs;

import com.clicker.core.SecurityAuditorAware;
import com.clicker.core.domain.user.models.entity.UserEntity;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {

    @Bean
    public AuditorAware<UserEntity> securityAuditorAware(EntityManager entityManager) {
        return new SecurityAuditorAware(entityManager);
    }
}
