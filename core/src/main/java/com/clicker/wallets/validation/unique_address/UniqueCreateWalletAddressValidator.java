package com.clicker.wallets.validation.unique_address;

import com.clicker.wallets.WalletsService;
import com.clicker.wallets.models.dto.FullWalletResponseDto;
import com.clicker.wallets.models.dto.WalletCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UniqueCreateWalletAddressValidator implements ConstraintValidator<UniqueWalletAddress, WalletCreateDto> {
    
    private final WalletsService walletsService;
    
    @Override
    public boolean isValid(WalletCreateDto walletCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        List<FullWalletResponseDto> walletsWithSameAddress = walletsService.findAllFullByAddress(walletCreateDto.address());
        List<Long> walletCurrencyIds = walletsWithSameAddress.stream()
                .map(FullWalletResponseDto::currencyId)
                .toList();
        if (walletCurrencyIds.contains(walletCreateDto.currencyId())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("This address is already in use!")
                    .addPropertyNode("address")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
