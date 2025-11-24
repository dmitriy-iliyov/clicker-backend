package com.clicker.core.domain.wallets.models.wallet_token.factory;

import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.wallet_token.WalletToken;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WalletTokenFactoryImpl implements WalletTokenFactory {

    @Override
    public WalletToken generateToken(FullWalletResponseDto fullWalletResponseDto) {
        return new WalletToken(
                fullWalletResponseDto.id(),
                fullWalletResponseDto.address(),
                fullWalletResponseDto.currencyId(),
                fullWalletResponseDto.currencyType().code(),
                fullWalletResponseDto.userId()
        );
    }
}