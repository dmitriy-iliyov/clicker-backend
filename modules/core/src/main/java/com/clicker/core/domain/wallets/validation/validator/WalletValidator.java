package com.clicker.core.domain.wallets.validation.validator;

import com.clicker.core.domain.user.models.dto.UserResponseDto;

import java.util.List;

public interface WalletValidator {

    void validateWalletOwnership(UserResponseDto userResponseDto, Long walletId);

    void validateWalletOwnership(UserResponseDto userResponseDto, List<Long> inputWalletIds);

}
