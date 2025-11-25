package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.Optional;
import java.util.Set;

public class PrefixAddressValidator implements PartlyAddressValidator {

    private final Set<String> prefixes;

    public PrefixAddressValidator(String ... prefixes) {
        this.prefixes = Set.of(prefixes);
    }

    @Override
    public Optional<WalletValidationResult> validate(String address) {
        for (String prefix : prefixes) {
            if (address.startsWith(prefix)) {
                return Optional.empty();
            }
        }
        return Optional.of(
                new WalletValidationResult("address", "Wallet address have not valid prefix")
        );
    }
}
