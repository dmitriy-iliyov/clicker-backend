package com.clicker.core.domain.user;

import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.exception.IncorrectPassword;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    void save(UserDto userDto);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    UserResponseDto update(UserUpdateDto userUpdateDto);

    void updatePasswordByEmail(String email, String password);

    void confirmUserByEmail(String email);

    Set<WalletResponseDto> findWalletsById(UUID id);

    UserResponseDto findWithWalletsById(UUID id);

    UserEntity findEntityById(UUID id);

    Optional<UserDto> systemFindByEmail(String mail);

    Optional<UserDto> systemFindById(UUID id);

    PageDto<PublicUserResponseDto> findAll(DefaultUserFilter filter, PageRequest pageRequest);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password) throws BadCredentialsException, IncorrectPassword;

    Optional<UserDto> systemFindByUsername(String username);
}
