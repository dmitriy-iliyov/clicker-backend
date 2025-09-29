package com.clicker.core.domain.wallets.models.wallet_token.serializing;

import com.clicker.core.domain.wallets.models.wallet_token.WalletToken;

public interface WalletTokenDeserializer {

    WalletToken deserialize(String token);

}
