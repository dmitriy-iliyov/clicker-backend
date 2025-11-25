package com.clicker.core.domain.wallets.validation.address.annotation;

import com.clicker.core.domain.wallets.models.dto.BaseWalletDto;
import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;
import com.clicker.core.domain.wallets.validation.address.WalletAddressValidationManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class WalletAddressConstraintValidator implements ConstraintValidator<WalletAddress, BaseWalletDto> {

    private final WalletAddressValidationManager manager;
    
    @Override
    public boolean isValid(BaseWalletDto wallet, ConstraintValidatorContext constraintValidatorContext) {
        List<WalletValidationResult> validationResults = manager.validate(wallet);
        if(validationResults.isEmpty()) {
            return true;
        }
        validationResults.forEach(result ->
                constraintValidatorContext.buildConstraintViolationWithTemplate(result.description())
                        .addPropertyNode(result.paramName())
                        .addConstraintViolation()
        );
        return false;
    }
}
