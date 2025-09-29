package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.models.dto.CurrencyResponseDto;
import com.clicker.core.domain.currency.models.dto.CurrencyUpdateDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CurrencyUpdateDecorator implements CurrencyUpdateService {

    private final CurrencyUpdateService delegate;
    private final Validator validator;

    public CurrencyUpdateDecorator(@Qualifier("currencyServiceImpl") CurrencyUpdateService delegate, Validator validator) {
        this.delegate = delegate;
        this.validator = validator;
    }

    @Override
    public CurrencyResponseDto updateByAdminPassword(String password, CurrencyUpdateDto dto) {
        Set<ConstraintViolation<CurrencyUpdateDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        return delegate.updateByAdminPassword(password, dto);
    }
}
