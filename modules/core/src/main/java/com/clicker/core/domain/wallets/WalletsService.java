package com.clicker.core.domain.wallets;

import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface WalletsService {

    WalletResponseDto save(UserEntity userEntity, WalletCreateDto walletCreateDto);

    boolean existsById(Long id);

    boolean existsByUserIdAndId(UserEntity user, Long id);

    WalletResponseDto findWithCurrencyById(Long id);

    FullWalletResponseDto findFullById(Long id);

    Set<WalletResponseDto> findAllWithCurrencyByUserId(UUID userId);

    Set<FullWalletResponseDto> findAllFullByAddress(String address);

    void setMainWallet(Long id, HttpServletResponse response);

    WalletResponseDto update(WalletUpdateDto walletUpdateDto);

    void updateBatch(Map<Long, WalletUpdateDto> dtos, Set<WalletEntity> entities);
    
    void delete(Long id);
}
