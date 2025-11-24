package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.mapper.CurrencyMapper;
import com.clicker.core.domain.currency.mapper.FullCurrencyMapper;
import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.CurrencyResponseDto;
import com.clicker.core.domain.currency.models.FullCurrencyResponseDto;
import com.clicker.core.exception.not_found.CurrencyNotFoundByCodeException;
import com.clicker.core.exception.not_found.CurrencyNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repository;
    private final CurrencyMapper mapper;
    private final FullCurrencyMapper fullMapper;

    @Transactional(readOnly = true)
    @Override
    public boolean existedById(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existedByCode(String code) {
        return repository.existsByCode(code);
    }

    @Transactional(readOnly = true)
    @Override
    public CurrencyResponseDto findById(Long id) {
        CurrencyEntity currencyEntity = repository.findById(id).orElseThrow(
                CurrencyNotFoundByIdException::new
        );
        return mapper.toResponseDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public CurrencyEntity findEntityById(Long id) {
        return repository.findById(id).orElseThrow(CurrencyNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public CurrencyResponseDto findByCode(String code) {
        CurrencyEntity currencyEntity = repository.findByCode(code).orElseThrow(
                CurrencyNotFoundByCodeException::new
        );
        return mapper.toResponseDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public FullCurrencyResponseDto findWithWalletsById(Long id) {
        CurrencyEntity currencyEntity = repository.findWithWalletsById(id).orElseThrow(CurrencyNotFoundByIdException::new);
        return fullMapper.toFullResponseDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CurrencyResponseDto> findAll() {
        List<CurrencyEntity> currencyEntities = repository.findAll();
        return mapper.toResponseDtoList(currencyEntities);
    }

    @Transactional
    @Override
    public void deleteByAdminPasswordAndId(String password, Long id) {
        repository.deleteById(id);
    }
}
