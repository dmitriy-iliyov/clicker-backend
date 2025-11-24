package com.clicker.core.domain.wallets;

import com.clicker.core.domain.user.UserService;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.exception.not_found.WalletNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletFacadeImpl implements WalletFacade {

    private final WalletsService walletsService;
    private final Validator validator;
    private final UserService userService;

    @Override
    public WalletResponseDto save(UUID userId, WalletCreateDto walletCreateDto) {
        UserEntity entity = userService.getReferenceById(userId);
        return walletsService.save(entity, walletCreateDto);
    }

    @Transactional
    @Override
    public void setMainWallet(UUID userId, Long id, HttpServletResponse response) {
        if (walletsService.existsByUserIdAndId(userService.getReferenceById(userId), id)) {
            throw new WalletNotFoundException();
        }
        walletsService.setMainWallet(id, response);
    }

    @Transactional
    @Override
    public WalletResponseDto update(UUID userId, WalletUpdateDto dto) {
        Set<ConstraintViolation<WalletUpdateDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        if (walletsService.existsByUserIdAndId(userService.getReferenceById(userId), dto.getId())) {
            throw new WalletNotFoundException();
        }
        return walletsService.update(dto);
    }

    @Transactional
    @Override
    public void deleteByUserIdAndId(UUID userId, Long id) {
        if (walletsService.existsByUserIdAndId(userService.getReferenceById(userId), id)) {
            throw new WalletNotFoundException();
        }
        walletsService.delete(id);
    }

    @Override
    public void deleteById(Long id) {
        walletsService.delete(id);
    }

    @Override
    public FullWalletResponseDto findFullById(Long id) {
        return walletsService.findFullById(id);
    }

    @Override
    public Set<WalletResponseDto> findAllWithCurrencyByUserId(UUID userId) {
        return walletsService.findAllWithCurrencyByUserId(userId);
    }
}