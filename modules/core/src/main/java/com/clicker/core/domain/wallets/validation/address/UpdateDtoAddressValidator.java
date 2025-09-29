package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateDtoAddressValidator implements ConstraintValidator<WalletAddress, WalletUpdateDto> {

    @Override
    public boolean isValid(WalletUpdateDto walletUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }

}
