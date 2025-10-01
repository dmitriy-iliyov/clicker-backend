package com.clicker.core.security.core.models.token;

import com.clicker.core.security.core.models.token.models.TokenEntity;

public interface DeactivateTokenService {
    void deactivate(TokenEntity token);
}
