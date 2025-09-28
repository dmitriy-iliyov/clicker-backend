package com.example.clicker.currency.mapper;

import com.example.clicker.currency.models.CurrencyEntity;
import com.example.clicker.currency.models.dto.CurrencyCreateDto;
import com.example.clicker.currency.models.dto.CurrencyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CurrencyMapper {

    CurrencyEntity toEntity(CurrencyCreateDto currencyCreateDto);

    @Named("toResponseDto")
    CurrencyResponseDto toResponseDto(CurrencyEntity currencyEntity);

    List<CurrencyResponseDto> toResponseDtoList(List<CurrencyEntity> currencyEntities);
}
