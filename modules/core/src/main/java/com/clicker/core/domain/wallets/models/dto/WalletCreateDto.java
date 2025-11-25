package com.clicker.core.domain.wallets.models.dto;


import com.clicker.core.domain.wallets.validation.address.annotation.WalletAddress;
import com.clicker.core.domain.wallets.validation.unique_address.UniqueWalletAddress;

@WalletAddress
@UniqueWalletAddress
public class WalletCreateDto extends BaseWalletDto {

    public WalletCreateDto(Long currencyId, String address) {
        super(currencyId, address);
    }
}
