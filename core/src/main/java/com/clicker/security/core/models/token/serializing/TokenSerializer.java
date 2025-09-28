package com.clicker.security.core.models.token.serializing;

import com.clicker.security.core.models.token.models.Token;

public interface TokenSerializer {

    String serialize(Token token);

}
