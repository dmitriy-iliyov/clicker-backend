package com.clicker.core.domain.wallets.models.wallet_token.factory;

import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.wallet_token.WalletToken;

public interface WalletTokenFactory {

    WalletToken generateToken(FullWalletResponseDto fullWalletResponseDto);

}
