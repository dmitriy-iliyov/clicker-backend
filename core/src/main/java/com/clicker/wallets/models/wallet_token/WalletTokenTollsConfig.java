package com.clicker.wallets.models.wallet_token;


import com.clicker.wallets.models.wallet_token.factory.WalletTokenFactory;
import com.clicker.wallets.models.wallet_token.factory.WalletTokenFactoryImpl;
import com.clicker.wallets.models.wallet_token.serializing.WalletTokenDeserializer;
import com.clicker.wallets.models.wallet_token.serializing.WalletTokenDeserializerImpl;
import com.clicker.wallets.models.wallet_token.serializing.WalletTokenSerializer;
import com.clicker.wallets.models.wallet_token.serializing.WalletTokenSerializerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletTokenTollsConfig {

    @Value("${clicker.api.wallet.jwt.secret}")
    private String SECRET;

    @Bean
    public WalletTokenFactory walletTokenFactory() {
        return new WalletTokenFactoryImpl();
    }

    @Bean
    public WalletTokenDeserializer walletTokenDeserializer() {
        return new WalletTokenDeserializerImpl(SECRET);
    }

    @Bean
    public WalletTokenSerializer walletTokenSerializer() {
        return new WalletTokenSerializerImpl(SECRET);
    }
}
