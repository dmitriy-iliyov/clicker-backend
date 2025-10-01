package com.clicker.auth;

import java.util.UUID;

public interface TokenUserDetailsRepository {
    boolean existsById(UUID id);
}
