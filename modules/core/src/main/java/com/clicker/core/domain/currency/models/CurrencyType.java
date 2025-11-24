package com.clicker.core.domain.currency.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyType {

    BTC("BTC"),
    ETH("ETH"),
    BNB("BNB"),
    SOL("SOL");

    private final String code;

    CurrencyType(String code) {
        this.code = code;
    }

    @JsonValue
    public String code() {
        return code;
    }
}
