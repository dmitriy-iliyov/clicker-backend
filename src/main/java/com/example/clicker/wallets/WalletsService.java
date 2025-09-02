package com.example.clicker.wallets;

import com.example.clicker.user.models.entity.UserEntity;
import com.example.clicker.wallets.models.dto.FullWalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletCreateDto;
import com.example.clicker.wallets.models.dto.WalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletUpdateDto;
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
