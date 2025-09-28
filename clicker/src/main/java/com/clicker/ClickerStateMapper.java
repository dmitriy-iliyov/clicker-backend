package com.clicker;

import com.clicker.models.ClickerStateDto;
import com.clicker.models.ClickerStateEntity;
import com.clicker.models.TemporaryClickerStateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClickerStateMapper {

    ClickerStateDto toDto(TemporaryClickerStateEntity entity);

    ClickerStateEntity toEntity(ClickerStateDto dto);

    ClickerStateDto toDto(ClickerStateEntity entity);

    void updateEntityFromDto(ClickerStateDto dto, @MappingTarget ClickerStateEntity entity);
}
