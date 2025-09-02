package com.example.clicker.wallets;

import com.example.clicker.user.UserService;
import com.example.clicker.user.models.dto.UserResponseDto;
import com.example.clicker.user.models.entity.UserEntity;
import com.example.clicker.wallets.models.dto.FullWalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletCreateDto;
import com.example.clicker.wallets.models.dto.WalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletUpdateDto;
import com.example.clicker.wallets.validation.validator.WalletValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletFacadeImpl implements WalletFacade {

    private final UserService userService;
    private final WalletsService walletsService;
    private final WalletValidator walletValidator;


    @Override
    public WalletResponseDto save(UUID userId, WalletCreateDto walletCreateDto) {
        UserEntity userEntity = userService.findEntityById(userId);
        return walletsService.save(userEntity, walletCreateDto);
    }

    @Override
    public void setMainWallet(UUID userId, Long id, HttpServletResponse response) {
        UserResponseDto userResponseDto = userService.findWithWalletsById(userId);
        walletValidator.validateWalletOwnership(userResponseDto, id);
        walletsService.setMainWallet(id, response);
    }

    @Override
    public WalletResponseDto update(UUID userId, WalletUpdateDto walletUpdateDto) {
        UserResponseDto userResponseDto = userService.findWithWalletsById(userId);
        walletValidator.validateWalletOwnership(userResponseDto, walletUpdateDto.id());
        return walletsService.update(walletUpdateDto);
    }

    @Override
    public void deleteByUserIdNId(UUID userId, Long id) {
        UserResponseDto userResponseDto = userService.findWithWalletsById(userId);
        walletValidator.validateWalletOwnership(userResponseDto, id);
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