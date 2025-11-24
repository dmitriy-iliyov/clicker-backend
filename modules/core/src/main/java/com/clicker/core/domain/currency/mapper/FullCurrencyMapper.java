package com.clicker.core.domain.currency.mapper;


import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.FullCurrencyResponseDto;
import com.clicker.core.domain.wallets.mapper.WalletMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = WalletMapper.class)
public interface FullCurrencyMapper {
    @Mapping(target = "wallets", qualifiedByName = "toResponseDtoSet", source = "wallets")
    FullCurrencyResponseDto toFullResponseDto(CurrencyEntity currencyEntity);
}
