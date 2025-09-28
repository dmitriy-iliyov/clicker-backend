package com.clicker.currency.validation.unique;

import com.clicker.currency.CurrencyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueCurrencyValidator implements ConstraintValidator<UniqueCurrency, String> {

    private final CurrencyService currencyService;

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext constraintValidatorContext) {

        if (currencyCode == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Illegal input!")
                    .addPropertyNode("code")
                    .addConstraintViolation();
            return false;
        }

        if (currencyService.existedByCode(currencyCode)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("This currency already exist!")
                    .addPropertyNode("code")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
