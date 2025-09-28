package com.clicker.user.mapper;


import com.clicker.security.core.models.authority.mapper.AuthorityMapper;
import com.clicker.user.models.dto.*;
import com.clicker.user.models.entity.UnconfirmedUserEntity;
import com.clicker.user.models.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class, AuthorityMapper.class})
public interface UserMapper {

    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    @Mapping(target = "username", qualifiedByName = "generateUsernameIfEmpty", source = "username")
    UnconfirmedUserEntity toUnconfirmedEntity(UserRegistrationDto userRegistrationDto);

    SystemUserDto toSystemDto(UnconfirmedUserEntity unconfirmedUserEntity);

    @Mapping(target = "authorities", qualifiedByName = "toAuthorityList", source = "authorities")
    SystemUserDto toSystemDto(UserEntity userEntity);

    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(SystemUserDto systemUserDto);

    @Mapping(target = "createdAt", qualifiedByName = "formatDate", source = "createdAt")
    @Mapping(target = "wallets", qualifiedByName = "toResponseDtoSet", source = "wallets")
    UserResponseDto toResponseDto(UserEntity userEntity);

    List<PublicUserResponseDto> toPublicResponseDto(List<UserEntity> userEntities);

    SystemUserUpdateDto toSystemUpdateDto(UserUpdateDto userUpdateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    @Mapping(target = "wallets", ignore = true)
    void updateEntityFromDto(SystemUserUpdateDto systemUserUpdateDto, @MappingTarget UserEntity userEntity);
}
