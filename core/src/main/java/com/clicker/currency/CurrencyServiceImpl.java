package com.clicker.currency;

import com.clicker.currency.mapper.CurrencyMapper;
import com.clicker.currency.mapper.FullCurrencyMapper;
import com.clicker.currency.models.CurrencyEntity;
import com.clicker.currency.models.dto.CurrencyCreateDto;
import com.clicker.currency.models.dto.CurrencyResponseDto;
import com.clicker.currency.models.dto.CurrencyUpdateDto;
import com.clicker.currency.models.dto.FullCurrencyResponseDto;
import com.clicker.general.exceptions.models.not_found.CurrencyNotFoundByCodeException;
import com.clicker.general.exceptions.models.not_found.CurrencyNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final FullCurrencyMapper fullCurrencyMapper;


    @Transactional
    @Override
    public void save(CurrencyCreateDto currencyCreateDto) {
        currencyRepository.save(currencyMapper.toEntity(currencyCreateDto));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existedById(Long id) {
        return currencyRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existedByCode(String code) {
        return currencyRepository.existsByCode(code);
    }

    @Transactional(readOnly = true)
    @Override
    public CurrencyResponseDto findById(Long id) {
        CurrencyEntity currencyEntity = currencyRepository.findById(id).orElseThrow(
                CurrencyNotFoundByIdException::new
        );
        return currencyMapper.toResponseDto(currencyEntity);
    }

    //vulnerability
    @Transactional(readOnly = true)
    @Override
    public CurrencyEntity findEntityById(Long id) {
        return currencyRepository.findById(id).orElseThrow(CurrencyNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public CurrencyResponseDto findByCode(String code) {
        CurrencyEntity currencyEntity = currencyRepository.findByCode(code).orElseThrow(
                CurrencyNotFoundByCodeException::new
        );
        return currencyMapper.toResponseDto(currencyEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public FullCurrencyResponseDto findWithWalletsById(Long id) {
        CurrencyEntity currencyEntity = currencyRepository.findWithWalletsById(id).orElseThrow(CurrencyNotFoundByIdException::new);
        return fullCurrencyMapper.toFullResponseDto(currencyEntity);
    }

    @Transactional
    @Override
    public CurrencyResponseDto updateByAdminPassword(String password, CurrencyUpdateDto currencyUpdateDto) {
        // check admin password
        CurrencyEntity currencyEntity = currencyRepository.findById(currencyUpdateDto.id()).orElseThrow(
                CurrencyNotFoundByIdException::new
        );
        currencyEntity.setCode(currencyUpdateDto.code());
        return currencyMapper.toResponseDto(currencyRepository.save(currencyEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CurrencyResponseDto> findAll() {
        List<CurrencyEntity> currencyEntities = currencyRepository.findAll();
        return currencyMapper.toResponseDtoList(currencyEntities);
    }

    @Transactional
    @Override
    public void deleteByAdminPasswordAndId(String password, Long id) {
        // check admin password
        currencyRepository.deleteById(id);
    }
}
