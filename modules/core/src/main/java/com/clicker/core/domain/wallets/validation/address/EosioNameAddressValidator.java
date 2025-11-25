package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.Optional;
import java.util.regex.Pattern;

public class EosioNameAddressValidator implements PartlyAddressValidator {

    private final Pattern pattern = Pattern.compile("^[a-z1-5]+$");

    @Override
    public Optional<WalletValidationResult> validate(String address) {
        if (!pattern.matcher(address).matches()) {
            return Optional.of(
                    new WalletValidationResult("address", "Address not match with EOSIO pattern")
            );
        }
        return Optional.empty();
    }
}
