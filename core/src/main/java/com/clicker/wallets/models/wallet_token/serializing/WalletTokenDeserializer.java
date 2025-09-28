package com.clicker.wallets.models.wallet_token.serializing;

import com.clicker.wallets.models.wallet_token.WalletToken;

public interface WalletTokenDeserializer {

    WalletToken deserialize(String token);

}
