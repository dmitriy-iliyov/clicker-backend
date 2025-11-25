package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.wallets.models.dto.BaseWalletDto;
import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;

import java.util.List;

public interface WalletAddressValidationManager {
    List<WalletValidationResult> validate(BaseWalletDto wallet);
}
