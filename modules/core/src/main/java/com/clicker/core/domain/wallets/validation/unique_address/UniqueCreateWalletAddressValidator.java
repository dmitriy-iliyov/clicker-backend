package com.clicker.core.domain.wallets.validation.unique_address;

import com.clicker.core.domain.wallets.WalletsService;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class UniqueCreateWalletAddressValidator implements ConstraintValidator<UniqueWalletAddress, WalletCreateDto> {
    
    private final WalletsService walletsService;
    
    @Override
    public boolean isValid(WalletCreateDto dto, ConstraintValidatorContext context) {
        Set<FullWalletResponseDto> walletsWithSameAddress = walletsService.findAllFullByAddress(dto.getAddress());
        List<Long> walletCurrencyIds = walletsWithSameAddress.stream()
                .map(FullWalletResponseDto::currencyId)
                .toList();
        if (walletCurrencyIds.contains(dto.getCurrencyId())) {
            context.buildConstraintViolationWithTemplate("This address is already in use!")
                    .addPropertyNode("address")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
