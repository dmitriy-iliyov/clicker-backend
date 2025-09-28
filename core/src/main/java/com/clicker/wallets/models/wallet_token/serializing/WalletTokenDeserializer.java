package com.example.clicker.wallets.models.wallet_token.serializing;

import com.example.clicker.wallets.models.wallet_token.WalletToken;

public interface WalletTokenDeserializer {

    WalletToken deserialize(String token);

}
