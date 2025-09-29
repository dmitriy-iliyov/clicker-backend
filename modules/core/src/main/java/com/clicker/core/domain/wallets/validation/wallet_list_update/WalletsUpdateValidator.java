package com.clicker.core.domain.wallets.validation.wallet_list_update;

import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class WalletsUpdateValidator implements ConstraintValidator<WalletsUpdate, List<WalletUpdateDto>> {

    @Override
    public boolean isValid(List<WalletUpdateDto> walletUpdateDtoSet, ConstraintValidatorContext constraintValidatorContext) {
        Set<Long> uniqueIds = new HashSet<>();
        Map<Pair<Long, String>, Long> wallets = new HashMap<>();
        Set<Pair<Long, String>> uniqueWallets = new HashSet<>();
        for (WalletUpdateDto walletUpdateDto : walletUpdateDtoSet) {
            Pair<Long, String> walletKey = Pair.of(walletUpdateDto.getCurrencyId(), walletUpdateDto.getAddress());
            if (!uniqueIds.add(walletUpdateDto.getId())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                                String.format(
                                        "Wallet with id %d duplicated!",
                                        walletUpdateDto.getId()))
                        .addPropertyNode("id")
                        .addConstraintViolation();
                return false;
            } else if (!uniqueWallets.add(walletKey)) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                                String.format(
                                        "Wallet details with id %d duplicates wallet details with id %d!",
                                        walletUpdateDto.getId(),
                                        wallets.get(walletKey))
                        )
                        .addPropertyNode("wallet")
                        .addConstraintViolation();
                return false;
            }
            wallets.put(walletKey, walletUpdateDto.getId());
        }
        return true;
    }
}
