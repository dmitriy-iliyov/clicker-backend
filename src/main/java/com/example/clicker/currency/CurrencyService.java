package com.example.clicker.currency;

import com.example.clicker.currency.models.CurrencyEntity;
import com.example.clicker.currency.models.dto.CurrencyCreateDto;
import com.example.clicker.currency.models.dto.CurrencyResponseDto;
import com.example.clicker.currency.models.dto.CurrencyUpdateDto;
import com.example.clicker.currency.models.dto.FullCurrencyResponseDto;

import java.util.List;

public interface CurrencyService {

    void save(CurrencyCreateDto currencyCreateDto);

    boolean existedByCode(String code);

    boolean existedById(Long id);

    CurrencyResponseDto updateByAdminPassword(String password, CurrencyUpdateDto currencyUpdateDto);

    CurrencyResponseDto findById(Long id);

    CurrencyResponseDto findByCode(String code);

    CurrencyEntity findEntityById(Long id);

    FullCurrencyResponseDto findWithWalletsById(Long id);

    List<CurrencyResponseDto> findAll();

    void deleteByAdminPasswordAndId(String password, Long id);
}
