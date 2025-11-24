package com.clicker.core.domain.wallets;

import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Set;
import java.util.UUID;

public interface WalletFacade {
    WalletResponseDto save(UUID userId, WalletCreateDto walletCreateDto);

    void setMainWallet(UUID userId, Long id, HttpServletResponse response);

    WalletResponseDto update(UUID userId, WalletUpdateDto walletUpdateDto);

    void deleteByUserIdAndId(UUID userId, Long id);

    void deleteById(Long id);

    FullWalletResponseDto findFullById(Long id);

    Set<WalletResponseDto> findAllWithCurrencyByUserId(UUID userId);
}
