package com.clicker.core.security.core.models.token.serializing;

import com.clicker.core.security.core.models.token.models.Token;

public interface TokenDeserializer {

    Token deserialize(String token);

}
