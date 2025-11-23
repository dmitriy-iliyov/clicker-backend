package com.clicker.core.domain.wallets.validation.validator;

import com.clicker.core.domain.user.models.dto.FullUserDto;
import com.clicker.core.domain.user.models.dto.UserResponseDto;

import java.util.Set;

public interface WalletValidator {
    void validateWalletOwnership(UserResponseDto user, Long walletId);
    void validateWalletOwnership(FullUserDto user, Set<Long> inputWalletIds);
}
