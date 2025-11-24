package com.clicker.core.domain.user;

import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.exception.IncorrectPassword;
import com.clicker.core.shared.PageDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    void save(ConfirmedUserDto confirmedUserDto);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Set<WalletResponseDto> findWalletsById(UUID id);

    UserResponseDto findWithWalletsById(UUID id);

    FullUserDto findFullById(UUID id);

    Optional<ShortUserDto> findShortByEmail(String mail);

    Optional<ShortUserDto> findShortByUsername(String username);

    UserEntity findEntityById(UUID id);

    PageDto<PublicUserResponseDto> findAll(DefaultUserFilter filter, PageRequest pageRequest);

    void confirmUserByEmail(String email);

    UserResponseDto update(UserUpdateDto userUpdateDto);

    void updatePasswordByEmail(String email, String password);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password) throws BadCredentialsException, IncorrectPassword;

}
