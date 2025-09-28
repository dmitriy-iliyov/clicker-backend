package com.clicker.wallets.validation.validator;

import com.clicker.general.exceptions.models.IllegalInputException;
import com.clicker.general.exceptions.models.validation.BelongWalletValidationException;
import com.clicker.user.models.dto.UserResponseDto;
import com.clicker.wallets.models.dto.WalletResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WalletValidatorImpl implements WalletValidator {

    @Override
    public void validateWalletOwnership(UserResponseDto userResponseDto, Long walletId) {
        if (walletId == null) {
            throw new IllegalInputException("Wallet id is null!");
        }

        List<Long> walletIds = userResponseDto.wallets()
                .stream()
                .map(WalletResponseDto::id)
                .toList();

        if (!walletIds.contains(walletId)) {
            throw new BelongWalletValidationException();
        }
    }

    @Override
    public void validateWalletOwnership(UserResponseDto userResponseDto, List<Long> inputWalletIds) {
        if (inputWalletIds == null) {
            throw new IllegalInputException("Wallets list is empty!");
        }

        List<Long> existedWalletIds = userResponseDto.wallets()
                .stream()
                .map(WalletResponseDto::id)
                .toList();

        for (Long walletId: inputWalletIds) {
            if (!existedWalletIds.contains(walletId)) {
                throw new BelongWalletValidationException();
            }
        }
    }
}
