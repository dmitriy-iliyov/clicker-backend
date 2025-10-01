package com.clicker.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClickerStateMapper {

    @Mapping(target = "userId", expression = "java(UUID.fromString(entity.userId()))")
    ClickerStateDto toDto(TemporaryClickerStateEntity entity);

    @Mapping(target = "userId", source = "id")
    ClickerStateDto toDto(ClickerStateEntity entity);

    @Mapping(target = "userId", expression = "java(dto.userId().toString())")
    TemporaryClickerStateEntity toTmpEntity(ClickerStateDto dto);

    @Mapping(target = "id", source = "userId")
    ClickerStateEntity toEntity(ClickerStateDto dto);

    @Mapping(target = "id", source = "userId")
    void updateEntityFromDto(ClickerStateDto dto, @MappingTarget ClickerStateEntity entity);
}
