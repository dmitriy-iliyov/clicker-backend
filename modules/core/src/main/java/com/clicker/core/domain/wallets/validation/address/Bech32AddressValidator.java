package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.Optional;
import java.util.regex.Pattern;

public class Bech32AddressValidator implements PartlyAddressValidator {

    private final Pattern pattern;

    public Bech32AddressValidator(String prefix) {
        pattern = Pattern.compile("^(" + prefix.trim() + "1)[0-9ac-hj-np-z]{11,71}$");
    }

    @Override
    public Optional<WalletValidationResult> validate(String address) {
        if(!pattern.matcher(address).matches()) {
            return Optional.of(
                    new WalletValidationResult("address", "Address not match with bech32 pattern")
            );
        }
        return Optional.empty();
    }
}
