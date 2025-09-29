package com.clicker.core.exception;

import com.clicker.contracts.exceptions.dto.ExceptionResponseDto;
import com.clicker.contracts.exceptions.models.Exception;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExceptionMapper {
    ExceptionResponseDto toDto(Exception exception);
}
