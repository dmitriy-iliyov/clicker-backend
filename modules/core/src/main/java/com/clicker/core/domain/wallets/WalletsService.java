package com.clicker.core.domain.wallets;

import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.models.dto.FullWalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WalletsService {

    WalletResponseDto save(UserEntity userEntity, WalletCreateDto walletCreateDto);

    boolean existedById(Long id);

    WalletResponseDto findWithCurrencyById(Long id);

    FullWalletResponseDto findFullById(Long id);

    Set<WalletResponseDto> findAllWithCurrencyByUserId(UUID userId);

    List<FullWalletResponseDto> findAllFullByAddress(String address);

    void setMainWallet(Long id, HttpServletResponse response);

    void updateUserWallets(UUID userId, List<WalletUpdateDto> walletUpdateDtos);

    WalletResponseDto update(WalletUpdateDto walletUpdateDto);

    void delete(Long id);

}
