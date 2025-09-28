package com.clicker.wallets.models.wallet_token.factory;

import com.clicker.wallets.models.dto.FullWalletResponseDto;
import com.clicker.wallets.models.wallet_token.WalletToken;

public interface WalletTokenFactory {

    WalletToken generateToken(FullWalletResponseDto fullWalletResponseDto);

}
