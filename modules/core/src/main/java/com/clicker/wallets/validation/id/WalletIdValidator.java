package com.clicker.wallets.validation.id;

import com.clicker.wallets.WalletsService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletIdValidator implements ConstraintValidator<WalletId, Long> {

    private final WalletsService walletsService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Wallet id shouldn't be null!")
                    .addConstraintViolation();
            return false;
        }

        if (!walletsService.existedById(id)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Wallet with this id isn't exist!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
