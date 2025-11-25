package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.currency.models.CurrencyType;
import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.ArrayList;
import java.util.List;

public class CompositeWalletAddressValidator implements WalletAddressValidator {

    private final CurrencyType type;
    private final List<WalletAddressValidator> validators;

    public CompositeWalletAddressValidator(CurrencyType type) {
        this.type = type;
        this.validators = new ArrayList<>();
    }

    public CompositeWalletAddressValidator(CurrencyType type, List<WalletAddressValidator> validators) {
        this.type = type;
        this.validators = validators;
    }

    public void addValidator(WalletAddressValidator validator) {
        if (validator.getType().equals(type)) {
            validators.add(validator);
        } else {
            throw new IllegalArgumentException("Validator should have the same currency type as composite validator");
        }
    }

    @Override
    public CurrencyType getType() {
        return type;
    }

    @Override
    public List<WalletValidationResult> validate(String address) {
        int minSize = Integer.MAX_VALUE;
        List<WalletValidationResult> result = new ArrayList<>();
        for (WalletAddressValidator validator : validators) {
            List<WalletValidationResult> localResult = validator.validate(address);
            if (localResult.isEmpty()) {
                return localResult;
            }
            int localResultSize = localResult.size();
            if (minSize > localResultSize) {
                minSize = localResultSize;
                result = localResult;
            }
        }
        return result;
    }
}
