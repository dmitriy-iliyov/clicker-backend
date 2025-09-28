package com.example.clicker.wallets.validation.address;

import com.example.clicker.wallets.models.dto.WalletUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateDtoAddressValidator implements ConstraintValidator<WalletAddress, WalletUpdateDto> {

    @Override
    public boolean isValid(WalletUpdateDto walletUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }

}
