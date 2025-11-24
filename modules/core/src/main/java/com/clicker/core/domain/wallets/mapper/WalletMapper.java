package com.clicker.core.domain.wallets.mapper;

import com.clicker.core.domain.currency.mapper.CurrencyMapper;
import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.wallets.models.WalletEntity;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CurrencyMapper.class})
public interface WalletMapper {

    @Mapping(target = "id", ignore = true)
    WalletEntity toEntity(WalletCreateDto dto);

    @Mapping(target = "currencyType", expression = "java(entity.getCurrency().getType())")
    WalletResponseDto toResponseDto(WalletEntity entity);

    @Named("toResponseDtoSet")
    Set<WalletResponseDto> toResponseDtoSet(Set<WalletEntity> wallets);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currency", source = "currency")
    void updateEntityFromDto(WalletUpdateDto dto,
                             @MappingTarget WalletEntity wallet,
                             CurrencyEntity currency);
}
