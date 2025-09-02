package com.example.clicker.wallets.validation.address;

import com.example.clicker.wallets.models.dto.WalletCreateDto;
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
