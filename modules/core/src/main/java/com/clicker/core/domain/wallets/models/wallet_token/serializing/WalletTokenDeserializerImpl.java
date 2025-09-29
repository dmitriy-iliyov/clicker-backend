package com.clicker.core.domain.wallets.models.wallet_token.serializing;

import com.clicker.core.domain.wallets.models.wallet_token.WalletToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.UUID;


@Log4j2
@Data
@RequiredArgsConstructor
public class WalletTokenDeserializerImpl implements WalletTokenDeserializer {

    private final String SECRET;
    private final Logger logger = LoggerFactory.getLogger(WalletTokenDeserializerImpl.class);

    @Override
    public WalletToken deserialize(String inputJwt) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Jwt<?, ?> jwt;
        try {
             jwt = Jwts.parser()
                     .verifyWith(key)
                     .build()
                     .parseSignedClaims(inputJwt);
            try{
                Claims claims = (Claims) jwt.getPayload();

                return new WalletToken(
                        Long.valueOf(claims.getSubject()),
                        String.valueOf(claims.get("address")),
                        Long.valueOf((String) claims.get("currencyId")),
                        String.valueOf(claims.get("currency")),
                        UUID.fromString((String) claims.get("userId"))
                );
            }catch (Exception e){
                logger.error("Exception when get claims and deserialize to Token: {}", e.getMessage());
            }
        }catch (Exception e){
            logger.error("Exception when check jwt signature: {}", e.getMessage());
        }
        return null;
    }
}
