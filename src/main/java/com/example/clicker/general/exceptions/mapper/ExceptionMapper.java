package com.example.clicker.general.exceptions.mapper;

import com.example.clicker.general.exceptions.dto.ExceptionResponseDto;
import com.example.clicker.general.exceptions.models.Exception;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExceptionMapper {
    ExceptionResponseDto toDto(Exception exception);
}
