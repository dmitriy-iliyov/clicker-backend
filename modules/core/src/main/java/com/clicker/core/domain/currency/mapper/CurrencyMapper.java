package com.clicker.core.domain.currency.mapper;

import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.CurrencyResponseDto;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CurrencyMapper {

    @Named("toResponseDto")
    @Mapping(target = "currencyType", expression = "java(entity.getType())")
    CurrencyResponseDto toResponseDto(CurrencyEntity entity);

    List<CurrencyResponseDto> toResponseDtoList(List<CurrencyEntity> currencyEntities);
}
