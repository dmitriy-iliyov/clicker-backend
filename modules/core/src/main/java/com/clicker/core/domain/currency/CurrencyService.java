package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.dto.CurrencyCreateDto;
import com.clicker.core.domain.currency.models.dto.CurrencyResponseDto;
import com.clicker.core.domain.currency.models.dto.FullCurrencyResponseDto;

import java.util.List;

public interface CurrencyService extends CurrencyUpdateService {

    void save(CurrencyCreateDto currencyCreateDto);

    boolean existedByCode(String code);

    boolean existedById(Long id);

    CurrencyResponseDto findById(Long id);

    CurrencyResponseDto findByCode(String code);

    CurrencyEntity findEntityById(Long id);

    FullCurrencyResponseDto findWithWalletsById(Long id);

    List<CurrencyResponseDto> findAll();

    void deleteByAdminPasswordAndId(String password, Long id);
}
