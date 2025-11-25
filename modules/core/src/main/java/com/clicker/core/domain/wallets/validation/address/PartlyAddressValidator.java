package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.Optional;

public interface PartlyAddressValidator {
    Optional<WalletValidationResult> validate(String address);
}
