package com.clicker.wallets.validation.wallet_list_update;

import com.clicker.wallets.models.dto.WalletUpdateDto;
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
            Pair<Long, String> walletKey = Pair.of(walletUpdateDto.currencyId(), walletUpdateDto.address());
            if (!uniqueIds.add(walletUpdateDto.id())) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                                String.format(
                                        "Wallet with id %d duplicated!",
                                        walletUpdateDto.id()))
                        .addPropertyNode("id")
                        .addConstraintViolation();
                return false;
            } else if (!uniqueWallets.add(walletKey)) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                                String.format(
                                        "Wallet details with id %d duplicates wallet details with id %d!",
                                        walletUpdateDto.id(),
                                        wallets.get(walletKey))
                        )
                        .addPropertyNode("wallet")
                        .addConstraintViolation();
                return false;
            }
            wallets.put(walletKey, walletUpdateDto.id());
        }
        return true;
    }
}
