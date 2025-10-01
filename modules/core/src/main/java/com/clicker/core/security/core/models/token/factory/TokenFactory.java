package com.clicker.core.security.core.models.token.factory;

import com.clicker.auth.Token;
import org.springframework.security.core.Authentication;

public interface TokenFactory {
    Token generateToken(Authentication authentication);
}
