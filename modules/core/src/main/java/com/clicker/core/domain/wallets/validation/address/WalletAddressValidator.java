package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.currency.models.CurrencyType;
import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.List;

public interface WalletAddressValidator {
    CurrencyType getType();
    List<WalletValidationResult> validate(String address);
}
