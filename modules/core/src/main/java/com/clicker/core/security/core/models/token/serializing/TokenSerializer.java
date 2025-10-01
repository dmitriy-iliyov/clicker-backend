package com.clicker.core.security.core.models.token.serializing;


import com.clicker.auth.Token;

public interface TokenSerializer {

    String serialize(Token token);

}
