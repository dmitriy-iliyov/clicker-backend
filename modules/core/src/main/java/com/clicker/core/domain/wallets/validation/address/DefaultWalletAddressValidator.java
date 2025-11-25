package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.currency.models.CurrencyType;
import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DefaultWalletAddressValidator implements WalletAddressValidator {

    private final CurrencyType type;
    private final List<PartlyAddressValidator> validators = new ArrayList<>();

    public void addValidator(PartlyAddressValidator validator) {
        validators.add(validator);
    }

    @Override
    public CurrencyType getType() {
        return type;
    }

    @Override
    public List<WalletValidationResult> validate(String address) {
        List<WalletValidationResult> results = new ArrayList<>();
        validators.forEach(validator ->
                validator.validate(address).ifPresent(results::add)
        );
        return results;
    }
}
