package com.clicker.core.domain.wallets;

import com.clicker.core.domain.currency.CurrencyService;
import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.mapper.WalletMapper;
import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.domain.wallets.models.wallet_token.WalletToken;
import com.clicker.core.domain.wallets.models.wallet_token.factory.WalletTokenFactory;
import com.clicker.core.domain.wallets.models.wallet_token.serializing.WalletTokenSerializer;
import com.clicker.core.exception.ExceptionUtils;
import com.clicker.core.exception.not_found.WalletNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class WalletsServiceImpl implements WalletsService {

    private final WalletsRepository repository;
    private final WalletMapper mapper;
    private final WalletTokenFactory tokenFactory;
    private final WalletTokenSerializer tokenSerializer;
    private final CurrencyService currencyService;

    @Transactional
    @Override
    public WalletResponseDto save(UserEntity entity, WalletCreateDto dto) {
        try {
            CurrencyEntity currency = currencyService.getReferenceById(dto.getCurrencyId());
            WalletEntity wallet = mapper.toEntity(dto);
            wallet.setCurrency(currency);
            wallet.setUser(entity);
            return mapper.toResponseDto(repository.save(wallet));
        } catch(ConstraintViolationException e) {
            log.error("Error when updating wallet", e);
            throw ExceptionUtils.resolveCurrencyIdError(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUserIdAndId(UserEntity user, Long id) {
        return repository.existsByUserAndId(user, id);
    }

    @Transactional(readOnly = true)
    @Override
    public WalletResponseDto findWithCurrencyById(Long id) {
        WalletEntity walletentity = repository.findWithCurrencyById(id).orElseThrow(WalletNotFoundException::new);
        return mapper.toResponseDto(walletentity);
    }

    @Transactional(readOnly = true)
    @Override
    public FullWalletResponseDto findFullById(Long id) {
        return repository.findFullById(id).orElseThrow(WalletNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<WalletResponseDto> findAllWithCurrencyByUserId(UUID userId) {
        return mapper.toResponseDtoSet(repository.findAllWithCurrencyByUserId(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public Set<FullWalletResponseDto> findAllFullByAddress(String address) {
        return repository.findAllFullByAddress(address);
    }

    @Transactional(readOnly = true)
    @Override
    public void setMainWallet(Long id, HttpServletResponse response) {
        FullWalletResponseDto fullWalletResponseDto = repository.findFullById(id).orElseThrow(
                WalletNotFoundException::new
        );
        WalletToken walletToken = tokenFactory.generateToken(fullWalletResponseDto);
        String walletJwt = tokenSerializer.serialize(walletToken);
        Cookie cookie = new Cookie("__Host-main_wallet", walletJwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    @Transactional
    @Override
    public WalletResponseDto update(WalletUpdateDto dto) {
        try {
            WalletEntity entity = repository.findWithCurrencyById(dto.getId()).orElseThrow(
                    WalletNotFoundException::new
            );
            mapper.updateEntityFromDto(dto, entity, currencyService.getReferenceById(dto.getCurrencyId()));
            return mapper.toResponseDto(repository.save(entity));
        } catch(ConstraintViolationException e) {
            log.error("Error when updating wallet, id={}", dto.getId(), e);
            throw ExceptionUtils.resolveCurrencyIdError(e);
        }
    }

    @Override
    public void updateBatch(Map<Long, WalletUpdateDto> dtos, Set<WalletEntity> entities) {
        for (WalletEntity wallet: entities) {
            WalletUpdateDto dto = dtos.get(wallet.getId());
            mapper.updateEntityFromDto(dto, wallet, currencyService.getReferenceById(dto.getCurrencyId()));
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
