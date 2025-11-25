package com.clicker.core.domain.wallets.validation.validator;

import com.clicker.core.domain.user.models.dto.FullUserDto;

import java.util.Set;

public interface WalletValidator {
    void validateWalletOwnership(FullUserDto user, Set<Long> inputWalletIds);
}
