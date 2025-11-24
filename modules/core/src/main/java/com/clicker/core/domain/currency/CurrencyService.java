package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.CurrencyResponseDto;
import com.clicker.core.domain.currency.models.FullCurrencyResponseDto;

import java.util.List;

public interface CurrencyService {
    boolean existedByCode(String code);

    boolean existedById(Long id);

    CurrencyResponseDto findById(Long id);

    CurrencyResponseDto findByCode(String code);

    CurrencyEntity findEntityById(Long id);

    FullCurrencyResponseDto findWithWalletsById(Long id);

    List<CurrencyResponseDto> findAll();

    void deleteByAdminPasswordAndId(String password, Long id);
}
