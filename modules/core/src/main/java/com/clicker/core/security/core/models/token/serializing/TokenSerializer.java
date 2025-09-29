package com.clicker.core.security.core.models.token.serializing;

import com.clicker.core.security.core.models.token.models.Token;

public interface TokenSerializer {

    String serialize(Token token);

}
