package com.clicker.core.security.core.models.token;

import java.time.Duration;
import java.util.UUID;

public interface ActiveTokenService {
    void activate(UUID id, Duration ttl);
}
