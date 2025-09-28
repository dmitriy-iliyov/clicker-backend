package com.example.clicker.wallets.models.wallet_token.factory;

import com.example.clicker.wallets.models.dto.FullWalletResponseDto;
import com.example.clicker.wallets.models.wallet_token.WalletToken;

public interface WalletTokenFactory {

    WalletToken generateToken(FullWalletResponseDto fullWalletResponseDto);

}
