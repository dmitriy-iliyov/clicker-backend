package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

public class LengthsAddressValidator implements PartlyAddressValidator {

    private final int minLengths;
    private final int maxLengths;

    public LengthsAddressValidator(int lengths) {
        this.minLengths = lengths;
        this.maxLengths = lengths;
    }

    public LengthsAddressValidator(int minLengths, int maxLengths) {
        if (minLengths > maxLengths) {
            throw new IllegalArgumentException("Min lengths should be less then max lengths");
        }
        this.minLengths = minLengths;
        this.maxLengths = maxLengths;
    }

    @Override
    public Optional<WalletValidationResult> validate(String address) {
        if (address.length() < minLengths) {
            return Optional.of(
                    new WalletValidationResult("address", "Address is too short")
            );
        } else if (address.length() > maxLengths) {
            return Optional.of(
                    new WalletValidationResult("address", "Address is too long")
            );
        }
        return Optional.empty();
    }
}
