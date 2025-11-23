package com.clicker.core.domain.wallets.validation.validator;

import com.clicker.contracts.exceptions.models.BaseBadException;
import com.clicker.core.domain.user.models.dto.FullUserDto;
import com.clicker.core.domain.user.models.dto.UserResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.exception.BelongWalletValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WalletValidatorImpl implements WalletValidator {

    @Override
    public void validateWalletOwnership(UserResponseDto userResponseDto, Long walletId) {
        if (walletId == null) {
            throw new BaseBadException("Wallet id is null!");
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
    public void validateWalletOwnership(FullUserDto dto, Set<Long> inputWalletIds) {
        if (inputWalletIds == null) {
            throw new BaseBadException("Wallets list is empty!");
        }
        List<Long> existedWalletIds = dto.wallets()
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
