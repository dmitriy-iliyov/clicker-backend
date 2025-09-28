package com.clicker.user;

import com.example.clicker.user.models.dto.*;
import com.example.clicker.user.models.entity.UserEntity;
import com.example.clicker.wallets.models.dto.WalletResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    void save(SystemUserDto systemUserDto);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    UserResponseDto update(SystemUserUpdateDto systemUserUpdateDto);

    void updatePasswordByEmail(String email, String password);

    void confirmUserByEmail(String email);

    Set<WalletResponseDto> findWalletsById(UUID id);

    UserResponseDto findWithWalletsById(UUID id);

    UserEntity findEntityById(UUID id);

    SystemUserDto systemFindByEmail(String mail);

    SystemUserDto systemFindById(UUID id);

    PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password) throws BadCredentialsException;

    SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto);

    SystemUserDto systemFindByUsername(String username);
}
