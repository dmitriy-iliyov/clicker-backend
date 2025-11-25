package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.Optional;
import java.util.regex.Pattern;

public class Base58AddressValidator implements PartlyAddressValidator {

    private final Pattern pattern = Pattern.compile("^[123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]+$");

    @Override
    public Optional<WalletValidationResult> validate(String address) {
        if (!pattern.matcher(address).matches()) {
            return Optional.of(
                    new WalletValidationResult("address", "Address not match with base58 pattern")
            );
        }
        return Optional.empty();
    }
}
