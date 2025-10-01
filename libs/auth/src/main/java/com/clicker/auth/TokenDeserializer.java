package com.clicker.auth;

public interface TokenDeserializer {

    Token deserialize(String token);

}
