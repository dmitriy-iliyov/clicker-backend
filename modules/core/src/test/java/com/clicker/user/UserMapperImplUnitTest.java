package com.clicker.user;

//@ExtendWith(MockitoExtension.class)
//public class UserMapperImplUnitTest {
//
//    @Mock
//    MapperUtils mapperUtils;
//
//    @Mock
//    AuthorityMapper authorityMapper;

//    @InjectMocks
//    UserMapperImpl userMapper;
//
//    @Test
//    void toUnconfirmedEntity_shouldReturnUnconfirmedUserEntity() {
//        String email = "test@example.com";
//        String password = "password";
//        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(email, password);
//        doReturn(password).when(mapperUtils).encodePassword(any());
//        UnconfirmedUserEntity requiredEntity = new UnconfirmedUserEntity();
//        requiredEntity.setEmail(email);
//        requiredEntity.setPassword(password);
//
//        UnconfirmedUserEntity mappedEntity = userMapper.toUnconfirmedEntity(userRegistrationDto);
//
//        assertEquals(mappedEntity, requiredEntity);
//        verify(mapperUtils, times(1)).encodePassword(any());
//    }
//
//    @Test
//    void fromUnconfirmedUserEntityToSystemDto_shouldReturnSystemUserDto() {
//        Long id = 1L;
//        String email = "test@example.com";
//        String password = "password";
//        List<AuthorityEntity> authorityList = new ArrayList<>(List.of(new AuthorityEntity(1L, Authority.ROLE_UNCONFIRMED_USER)));
//        Instant createdAt = Instant.now();
//        UnconfirmedUserEntity unconfirmedUserEntity = new UnconfirmedUserEntity(email, password);
//        SystemUserDto requiredDto = new SystemUserDto(id, email, password,
//                new ArrayList<>(List.of(Authority.ROLE_UNCONFIRMED_USER)), createdAt, null);
//        doReturn(List.of(Authority.ROLE_UNCONFIRMED_USER)).when(authorityMapper).toAuthorityList(any());
//
//        SystemUserDto mapperDto = userMapper.toSystemDto(unconfirmedUserEntity);
//
//        assertEquals(mapperDto, requiredDto);
//        verify(authorityMapper, times(1)).toAuthorityList(any());
//    }

//    @Test
//    void fromUserEntityToSystemDto_shouldReturnSystemUserDto() {
//        Long id = 1L;
//        String email = "test@example.com";
//        String password = "password";
//        List<AuthorityEntity> authorityList = new ArrayList<>(
//                List.of(new AuthorityEntity(1L, Authority.ROLE_UNCONFIRMED_USER), new AuthorityEntity(2L, Authority.ROLE_USER)));
//        Instant createdAt = Instant.now();
//        Instant updatedAt = createdAt.plus(Duration.ofHours(1));
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
//        userEntity.setEmail(email);
//        userEntity.setPassword(password);
//        userEntity.setAuthorities(authorityList);
//        userEntity.setCreatedAt(createdAt);
//        userEntity.setUpdatedAt(updatedAt);
//        SystemUserDto requiredDto = new SystemUserDto(id, email, password,
//                new ArrayList<>(List.of(Authority.ROLE_UNCONFIRMED_USER, Authority.ROLE_USER)), createdAt, null);
//        doReturn(List.of(Authority.ROLE_UNCONFIRMED_USER, Authority.ROLE_USER)).when(authorityMapper).toAuthorityList(any());
//
//        SystemUserDto mapperDto = userMapper.toSystemDto(userEntity);
//
//        assertEquals(mapperDto, requiredDto);
//        verify(authorityMapper, times(1)).toAuthorityList(any());
//    }

//    @Test
//    void toUserEntity_shouldReturnUserEntityWithNullAuthorities() {
//        Long id = 1L;
//        String email = "test@example.com";
//        String password = "password";
//        Instant createdAt = Instant.now();
//        Instant updatedAt = createdAt.plus(Duration.ofHours(1));
//        SystemUserDto dto = new SystemUserDto(id, email, password,
//                new ArrayList<>(List.of(Authority.ROLE_UNCONFIRMED_USER, Authority.ROLE_USER)), createdAt, null);
//        UserEntity requiredEntity = new UserEntity();
//        requiredEntity.setId(1L);
//        requiredEntity.setEmail(email);
//        requiredEntity.setPassword(password);
//        requiredEntity.setAuthorities(null);
//        requiredEntity.setCreatedAt(createdAt);
//        requiredEntity.setUpdatedAt(updatedAt);
//
//        UserEntity mappedEntity = userMapper.toEntity(dto);
//
//        assertEquals(mappedEntity, requiredEntity);
//    }

//    @Test
//    void fromUserEntityToResponseDto_shouldReturnUserResponseDto() {
//
//    }

//    @Mapping(target = "createdAt", qualifiedByName = "formatDate", source = "createdAt")
//    @Mapping(target = "wallets", qualifiedByName = "toResponseDtoSet", source = "wallets")
//    UserResponseDto toResponseDto(UserEntity userEntity);
//
//    @Mapping(target = "createdAt", qualifiedByName = "formatDate", source = "createdAt")
//    UserResponseDto toResponseDto(UnconfirmedUserEntity unconfirmedUserEntity);
//
//    List<PublicUserResponseDto> toPublicResponseDto(List<UserEntity> userEntities);
//
//    SystemUserUpdateDto toSystemUpdateDto(UserUpdateDto userUpdateDto);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "password", qualifiedByName = "encodePassword", source = "password")
//    @Mapping(target = "wallets", ignore = true)
//    void updateEntityFromDto(SystemUserUpdateDto systemUserUpdateDto, @MappingTarget UserEntity userEntity);
//}
