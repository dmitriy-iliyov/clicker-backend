package com.example.clicker.wallets.validation.validator;

import com.example.clicker.user.models.dto.UserResponseDto;

import java.util.List;

public interface WalletValidator {

    void validateWalletOwnership(UserResponseDto userResponseDto, Long walletId);

    void validateWalletOwnership(UserResponseDto userResponseDto, List<Long> inputWalletIds);

}
