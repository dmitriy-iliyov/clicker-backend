package com.clicker.core.domain.wallets.validation.address;

import com.clicker.core.domain.currency.CurrencyService;
import com.clicker.core.domain.currency.models.CurrencyResponseDto;
import com.clicker.core.domain.currency.models.CurrencyType;
import com.clicker.core.domain.wallets.models.dto.BaseWalletDto;
import com.clicker.core.domain.wallets.models.dto.WalletValidationResult;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WalletAddressValidationManagerImpl implements WalletAddressValidationManager {

    private final Map<CurrencyType, WalletAddressValidator> validators;
    private final CurrencyService currencyService;

    public WalletAddressValidationManagerImpl(List<WalletAddressValidator> validators, CurrencyService currencyService) {
        this.validators = new EnumMap<>(
                validators.stream()
                        .collect(Collectors.toMap(WalletAddressValidator::getType, Function.identity()))
        );
        this.currencyService = currencyService;
    }

    @Override
    public List<WalletValidationResult> validate(BaseWalletDto wallet) {
        CurrencyResponseDto dto = currencyService.findById(wallet.getCurrencyId());
        WalletAddressValidator validator = validators.get(dto.currencyType());
        if (validator == null) {
            throw new RuntimeException("Cannot found wallet validator for currencyType=%s"
                    .formatted(dto.currencyType().code()));
        }
        return validator.validate(wallet.getAddress());
    }
}
