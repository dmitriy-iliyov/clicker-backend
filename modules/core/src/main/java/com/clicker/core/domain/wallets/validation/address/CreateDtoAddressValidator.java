package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateDtoAddressValidator implements ConstraintValidator<WalletAddress, WalletCreateDto> {

    @Override
    public boolean isValid(WalletCreateDto walletCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }

    private boolean btcValidation() {
        return true;
    }
}
