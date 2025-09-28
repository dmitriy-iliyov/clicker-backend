package com.clicker.wallets.validation.unique_address;

import com.clicker.wallets.WalletsService;
import com.clicker.wallets.models.dto.FullWalletResponseDto;
import com.clicker.wallets.models.dto.WalletUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UniqueUpdateWalletAddressValidator implements ConstraintValidator<UniqueWalletAddress, WalletUpdateDto> {

    private final WalletsService walletsService;

    @Override
    public boolean isValid(WalletUpdateDto walletUpdateDto, ConstraintValidatorContext constraintValidatorContext) {

        List<FullWalletResponseDto> fullWalletResponseDtos = walletsService.findAllFullByAddress(walletUpdateDto.address());

        Map<Long, List<FullWalletResponseDto>> currencyIds = fullWalletResponseDtos.stream()
                .collect(Collectors.groupingBy(FullWalletResponseDto::currencyId));
        if (currencyIds.containsKey(walletUpdateDto.currencyId())) {
            List<FullWalletResponseDto> wallets = currencyIds.get(walletUpdateDto.currencyId());
            if (wallets.size() == 1) {
                FullWalletResponseDto fullWalletResponseDto = wallets.get(0);
                if (fullWalletResponseDto.address().equals(walletUpdateDto.address()) &&
                        fullWalletResponseDto.id().equals(walletUpdateDto.id())) {
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
