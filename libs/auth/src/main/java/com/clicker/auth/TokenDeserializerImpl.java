package com.clicker.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@RequiredArgsConstructor
@Slf4j
public class TokenDeserializerImpl implements TokenDeserializer {

    private final String SECRET;

    @Override
    public Token deserialize(String inputJwt) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Jwt<?, ?> jwt;
        try {
             jwt = Jwts.parser()
                     .verifyWith(key)
                     .build()
                     .parseSignedClaims(inputJwt);
            try{
                Claims claims = (Claims) jwt.getPayload();
                UUID id = UUID.fromString(claims.getId());
                UUID subjectId = UUID.fromString(claims.getSubject());
                List<String> authorities = claims.get("authorities", List.class);
                Instant issuedAt = claims.getIssuedAt().toInstant();
                Instant expiresAt = claims.getExpiration().toInstant();
                if (expiresAt != null) {
                    if (Instant.now().isBefore(expiresAt)) {
                        return new Token(id, subjectId, authorities, issuedAt, expiresAt);
                    }
                }
                throw new CookieJwtExpiredException("Session expired");
            }catch (Exception e){
                log.error("Exception when get claims and deserialize to Token: {}", e.getMessage());
            }
        }catch (Exception e){
            log.error("Exception when check jwt signature: {}", e.getMessage());
        }
        return null;
    }
}
