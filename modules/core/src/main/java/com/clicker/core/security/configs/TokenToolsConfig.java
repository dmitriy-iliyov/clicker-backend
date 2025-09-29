package com.clicker.core.security.configs;


import com.clicker.core.domain.user.UserFacade;
import com.clicker.core.security.core.models.token.factory.TokenFactory;
import com.clicker.core.security.core.models.token.factory.TokenFactoryImpl;
import com.clicker.core.security.core.models.token.serializing.TokenDeserializer;
import com.clicker.core.security.core.models.token.serializing.TokenDeserializerImpl;
import com.clicker.core.security.core.models.token.serializing.TokenSerializer;
import com.clicker.core.security.core.models.token.serializing.TokenSerializerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenToolsConfig {

    private final UserFacade userFacade;

    @Value("${clicker.api.auth.jwt.secret}")
    private String SECRET;


    @Bean
    public TokenSerializer tokenSerializer(){
        return new TokenSerializerImpl(SECRET);
    }

    @Bean
    public TokenDeserializer tokenDeserializer() {
        return new TokenDeserializerImpl(SECRET);
    }

    @Bean
    public TokenFactory tokenFactory() {
        return new TokenFactoryImpl(userFacade);
    }

}
