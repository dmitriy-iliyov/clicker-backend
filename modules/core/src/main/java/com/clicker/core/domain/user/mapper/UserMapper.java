package com.clicker.core.domain.user.mapper;


import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.models.entity.UnconfirmedUserEntity;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.security.core.models.authority.mapper.AuthorityMapper;
import com.clicker.core.security.core.models.authority.models.Authority;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {MapperUtils.class, AuthorityMapper.class})
public interface UserMapper {

    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    @Mapping(target = "username", qualifiedByName = "generateUsernameIfEmpty", source = "username")
    UnconfirmedUserEntity toUnconfirmedEntity(UserRegistrationRequest request);

    UserDto toDto(UnconfirmedUserEntity entity);

    @Mapping(target = "authorities", qualifiedByName = "toAuthorityList", source = "authorities")
    UserDto toDto(UserEntity entity);

    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(UserDto dto);

    @Mapping(target = "createdAt", qualifiedByName = "formatDate", source = "createdAt")
    @Mapping(target = "wallets", qualifiedByName = "toResponseDtoSet", source = "wallets")
    UserResponseDto toResponseDto(UserEntity entity);

    List<PublicUserResponseDto> toPublicResponseDto(List<UserEntity> entities);

    UserUpdateDto toUpdateDto(UUID id, UserUpdateRequest request, String avatarUrl, List<Authority> authorities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
    @Mapping(target = "wallets", ignore = true)
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget UserEntity entity);
}
