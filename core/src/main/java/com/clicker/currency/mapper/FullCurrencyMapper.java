package com.clicker.currency.mapper;


import com.clicker.currency.models.CurrencyEntity;
import com.clicker.currency.models.dto.FullCurrencyResponseDto;
import com.clicker.wallets.mapper.WalletMapper;
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
