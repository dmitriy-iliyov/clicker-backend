package com.clicker.wallets;

import com.example.clicker.currency.CurrencyService;
import com.example.clicker.currency.models.CurrencyEntity;
import com.clicker.general.exceptions.models.not_found.WalletNotFoundException;
import com.example.clicker.user.models.entity.UserEntity;
import com.example.clicker.wallets.mapper.WalletMapper;
import com.example.clicker.wallets.models.WalletEntity;
import com.example.clicker.wallets.models.dto.FullWalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletCreateDto;
import com.example.clicker.wallets.models.dto.WalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletUpdateDto;
import com.example.clicker.wallets.models.wallet_token.WalletToken;
import com.example.clicker.wallets.models.wallet_token.factory.WalletTokenFactory;
import com.example.clicker.wallets.models.wallet_token.serializing.WalletTokenSerializer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WalletsServiceImpl implements WalletsService {

    private final WalletsRepository walletsRepository;
    private final WalletMapper walletMapper;
    private final WalletTokenFactory walletTokenFactory;
    private final WalletTokenSerializer walletTokenSerializer;
    private final CurrencyService currencyService;

    @Transactional
    @Override
    public WalletResponseDto save(UserEntity userEntity, WalletCreateDto walletCreateDto) {
        CurrencyEntity currencyEntity = currencyService.findEntityById(walletCreateDto.currencyId());
        WalletEntity walletEntity = walletMapper.toEntity(walletCreateDto);
        walletEntity.setCurrency(currencyEntity);
        walletEntity.setUser(userEntity);
        return walletMapper.toResponseDto(walletsRepository.save(walletEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existedById(Long id) {
        return walletsRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public WalletResponseDto findWithCurrencyById(Long id) {
        WalletEntity walletentity = walletsRepository.findWithCurrencyById(id).orElseThrow(
                WalletNotFoundException::new
        );
        return walletMapper.toResponseDto(walletentity);
    }

    @Transactional(readOnly = true)
    @Override
    public FullWalletResponseDto findFullById(Long id) {
        return walletsRepository.findFullById(id).orElseThrow(
                WalletNotFoundException::new
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Set<WalletResponseDto> findAllWithCurrencyByUserId(UUID userId) {
        return walletMapper.toResponseDtoSet(walletsRepository.findAllWithCurrencyByUserId(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<FullWalletResponseDto> findAllFullByAddress(String address) {
        return walletsRepository.findAllFullByAddress(address);
    }

    @Transactional(readOnly = true)
    @Override
    public void setMainWallet(Long id, HttpServletResponse response) {
        FullWalletResponseDto fullWalletResponseDto = walletsRepository.findFullById(id).orElseThrow(
                WalletNotFoundException::new
        );

        WalletToken walletToken = walletTokenFactory.generateToken(fullWalletResponseDto);
        System.out.println(walletToken);
        String walletJwt = walletTokenSerializer.serialize(walletToken);

        Cookie cookie = new Cookie("__Host-main_wallet", walletJwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }


    @Transactional
    @Override
    public WalletResponseDto update(WalletUpdateDto walletUpdateDto) {
        WalletEntity walletEntity = walletsRepository.findWithCurrencyById(walletUpdateDto.id()).orElseThrow(
                WalletNotFoundException::new
        );
        walletMapper.updateEntityFromDto(walletUpdateDto, walletEntity, currencyService);
        return walletMapper.toResponseDto(walletsRepository.save(walletEntity));
    }

    @Transactional
    @Override
    public void updateUserWallets(UUID userId, List<WalletUpdateDto> walletUpdateDtos) {
        Map<Long, WalletEntity> existedWallets = walletsRepository.findAllWithCurrencyByUserId(userId).stream()
                .collect(Collectors.toMap(WalletEntity::getId, walletEntity -> walletEntity));

        Set<WalletEntity> updatedWallets = walletUpdateDtos.stream()
                .map(walletUpdateDto -> {
                    WalletEntity walletEntity = existedWallets.get(walletUpdateDto.id());
                    walletMapper.updateEntityFromDto(walletUpdateDto, walletEntity, currencyService);
                    return walletEntity;
                })
                .collect(Collectors.toSet());

        walletsRepository.saveAll(updatedWallets);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        walletsRepository.deleteById(id);
    }
}
