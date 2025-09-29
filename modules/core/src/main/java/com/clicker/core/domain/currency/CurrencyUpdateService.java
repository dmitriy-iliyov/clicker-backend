package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.models.dto.CurrencyResponseDto;
import com.clicker.core.domain.currency.models.dto.CurrencyUpdateDto;

public interface CurrencyUpdateService {
    CurrencyResponseDto updateByAdminPassword(String password, CurrencyUpdateDto dto);
}
