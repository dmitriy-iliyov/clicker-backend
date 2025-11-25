package com.clicker.core.domain.wallets.validation.unique_address;

import com.clicker.core.domain.wallets.WalletsService;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UniqueUpdateWalletAddressValidator implements ConstraintValidator<UniqueWalletAddress, WalletUpdateDto> {

    private final WalletsService walletsService;

    @Override
    public boolean isValid(WalletUpdateDto walletUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        Set<FullWalletResponseDto> fullWalletResponseDtos = walletsService.findAllFullByAddress(walletUpdateDto.getAddress());
        Map<Long, List<FullWalletResponseDto>> currencyIds = fullWalletResponseDtos.stream()
                .collect(Collectors.groupingBy(FullWalletResponseDto::currencyId));
        if (currencyIds.containsKey(walletUpdateDto.getCurrencyId())) {
            List<FullWalletResponseDto> wallets = currencyIds.get(walletUpdateDto.getCurrencyId());
            if (wallets.size() == 1) {
                FullWalletResponseDto fullWalletResponseDto = wallets.get(0);
                if (fullWalletResponseDto.address().equals(walletUpdateDto.getAddress()) &&
                        fullWalletResponseDto.id().equals(walletUpdateDto.getId())) {
                    return true;
                }
            }
            constraintValidatorContext.buildConstraintViolationWithTemplate("This address is already in use!")
                    .addPropertyNode("address")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
