package com.clicker.user.mapper;

import com.clicker.wallets.mapper.WalletMapper;
import com.clicker.wallets.models.WalletEntity;
import com.clicker.wallets.models.dto.WalletResponseDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;


@Named("MapperUtils")
@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final PasswordEncoder passwordEncoder;
    private final WalletMapper walletMapper;

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("formatDate")
    public String formatDate(Instant unformattedDate) {
        Date date = Date.from(unformattedDate);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
        return formatter.format(date);
    }

    @Named("toResponseDtoSet")
    public Set<WalletResponseDto> toResponseDtoSet(Set<WalletEntity> walletEntities) {
        return walletMapper.toResponseDtoSet(walletEntities);
    }

    @Named("generateUsernameIfEmpty")
    public String generateUsernameIfEmpty(String username) {
        if (!username.isBlank()) {
            return username;
        }
        return "User" + UUID.randomUUID();
    }
}
