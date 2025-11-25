package com.clicker.core.configs;

import com.clicker.core.domain.currency.models.CurrencyType;
import com.clicker.core.domain.wallets.validation.address.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WalletValidatorsConfig {

    @Bean
    public WalletAddressValidator solanaAddressValidator() {
        DefaultWalletAddressValidator validator = new DefaultWalletAddressValidator(CurrencyType.SOLANA);
        validator.addValidator(new LengthsAddressValidator(43, 44));
        validator.addValidator(new Base58AddressValidator());
        return validator;
    }

    @Bean
    public WalletAddressValidator aptosAddressValidator() {
        DefaultWalletAddressValidator validator = new DefaultWalletAddressValidator(CurrencyType.APTOS);
        validator.addValidator(new LengthsAddressValidator(1, 66));
        validator.addValidator(new PrefixAddressValidator("0x"));
        return validator;
    }

    @Bean
    public WalletAddressValidator suiAddressValidator() {
        DefaultWalletAddressValidator validator = new DefaultWalletAddressValidator(CurrencyType.SUI);
        validator.addValidator(new LengthsAddressValidator(1, 66));
        validator.addValidator(new PrefixAddressValidator("0x"));
        return validator;
    }

    @Bean
    public WalletAddressValidator polygonAddressValidator() {
        DefaultWalletAddressValidator validator = new DefaultWalletAddressValidator(CurrencyType.POLYGON);
        validator.addValidator(new LengthsAddressValidator(42));
        validator.addValidator(new PrefixAddressValidator("0x"));
        return validator;
    }

    @Bean
    public WalletAddressValidator waxAddressValidator() {
        CurrencyType currencyType = CurrencyType.WAX;
        DefaultWalletAddressValidator accountValidator = new DefaultWalletAddressValidator(currencyType);
        accountValidator.addValidator(new LengthsAddressValidator(12));
        accountValidator.addValidator(new EosioNameAddressValidator());
        DefaultWalletAddressValidator pubkeyValidator = new DefaultWalletAddressValidator(currencyType);
        pubkeyValidator.addValidator(new LengthsAddressValidator(51, 53));
        pubkeyValidator.addValidator(new PrefixAddressValidator("PUB_K1_", "EOS"));
        return new CompositeWalletAddressValidator(
                currencyType,
                new ArrayList<>(List.of(accountValidator, pubkeyValidator))
        );
    }
}
