package com.clicker.core.exception;

import com.clicker.core.exception.not_found.CurrencyNotFoundByIdException;
import lombok.experimental.UtilityClass;
import org.hibernate.exception.ConstraintViolationException;

@UtilityClass
public class ExceptionUtils {

    public static RuntimeException resolveCurrencyIdError(ConstraintViolationException e) {
        String constraintName = e.getConstraintName();
        if (constraintName != null && constraintName.contains("currency_id")) {
            return new CurrencyNotFoundByIdException();
        }
        throw e;
    }
}
