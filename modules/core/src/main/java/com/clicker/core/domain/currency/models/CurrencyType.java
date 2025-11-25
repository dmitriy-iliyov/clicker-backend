package com.clicker.core.domain.currency.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyType {

    SOLANA("SOL"),
    APTOS("APT"),
    SUI("SUI"),
    POLYGON("PoS"),
    WAX("WAXP");

    private final String code;

    CurrencyType(String code) {
        this.code = code;
    }

    @JsonValue
    public String code() {
        return code;
    }
}
