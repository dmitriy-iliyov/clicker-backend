package com.example.clicker.wallets.models.wallet_token;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletToken {

    private Long walletId;
    private String walletAddress;
    private Long currencyId;
    private String currencyCode;
    private UUID userId;

}