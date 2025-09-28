package com.clicker.wallets.mapper;

import com.example.clicker.currency.mapper.CurrencyMapper;
import com.example.clicker.currency.models.CurrencyEntity;
import com.example.clicker.wallets.models.WalletEntity;
import com.example.clicker.wallets.models.dto.WalletCreateDto;
import com.example.clicker.wallets.models.dto.WalletResponseDto;
import com.example.clicker.wallets.models.dto.WalletUpdateDto;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CurrencyMapper.class})
public interface WalletMapper {

    @Mapping(target = "id", ignore = true)
    WalletEntity toEntity(WalletCreateDto walletCreateDto);

    @Mapping(target = "currencyCode", source = "currency", qualifiedByName = "getCurrencyCode")
    WalletResponseDto toResponseDto(WalletEntity walletEntity);

    @Named("toResponseDtoSet")
    Set<WalletResponseDto> toResponseDtoSet(Set<WalletEntity> walletEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "getCurrencyById")
    void updateEntityFromDto(WalletUpdateDto walletUpdateDto,
                             @MappingTarget WalletEntity walletEntity,
                             @Context CurrencyService currencyService);

    @Named("getCurrencyById")
    default CurrencyEntity getCurrencyById(Long id, @Context CurrencyService currencyService) {
        return currencyService.findEntityById(id);
    }

    @Named("getCurrencyCode")
    default String getCurrencyCode(CurrencyEntity currencyEntity) {
        return currencyEntity.getCode();
    }
}
