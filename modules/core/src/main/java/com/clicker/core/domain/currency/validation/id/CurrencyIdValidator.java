package com.clicker.core.domain.currency.validation.id;

import com.clicker.core.domain.currency.CurrencyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrencyIdValidator implements ConstraintValidator<CurrencyId, Long> {

    private final CurrencyService currencyService;

    @Override
    public boolean isValid(Long currencyId, ConstraintValidatorContext constraintValidatorContext) {
        if (currencyService == null) {
            throw new IllegalStateException("Currency validator not configured!");
        }

        if (currencyId == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Currency shouldn't be null!")
                    .addConstraintViolation();
            return false;
        }
        if (!currencyService.existedById(currencyId)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Unsupported currency!")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
