package com.clicker.currency.validation.update;

import com.clicker.currency.CurrencyService;
import com.clicker.currency.models.dto.CurrencyResponseDto;
import com.clicker.currency.models.dto.CurrencyUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CurrencyUpdateValidator implements ConstraintValidator<CurrencyUpdate, CurrencyUpdateDto> {

    private final CurrencyService currencyService;

    @Override
    public boolean isValid(CurrencyUpdateDto currencyUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        if (currencyUpdateDto == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Illegal input!")
                    .addPropertyNode("code")
                    .addConstraintViolation();
            return false;
        }
        if (currencyService.existedByCode(currencyUpdateDto.code())) {
            CurrencyResponseDto currencyResponseDto = currencyService.findByCode(currencyUpdateDto.code());
            if (currencyResponseDto != null && !currencyResponseDto.id().equals(currencyUpdateDto.id())) {
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("Invalid currency id or this currency already exist!")
                        .addPropertyNode("code")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
