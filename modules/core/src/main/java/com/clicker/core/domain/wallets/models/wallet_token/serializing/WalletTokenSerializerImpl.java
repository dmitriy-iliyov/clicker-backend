package com.clicker.core.domain.wallets.models.wallet_token.serializing;

import com.clicker.core.domain.wallets.models.wallet_token.WalletToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;


@Log4j2
@RequiredArgsConstructor
public class WalletTokenSerializerImpl implements WalletTokenSerializer {

    private final String SECRET;

    @Override
    public String serialize(WalletToken walletToken) {

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        Header header = Jwts.header()
                .add("alg", key.getAlgorithm())
                .add("typ", "JWT"
                ).build();

        Claims claims = Jwts.claims()
                .subject(String.valueOf(walletToken.getWalletId()))
                .add("address", walletToken.getWalletAddress())
                .add("currencyId", String.valueOf(walletToken.getCurrencyId()))
                .add("currency", walletToken.getCurrencyCode())
                .add("userId",String.valueOf(walletToken.getUserId()))
                .build();

        return Jwts.builder()
                .header().add(header).and()
                .claims(claims)
                .signWith(key)
                .compact();

    }
}
