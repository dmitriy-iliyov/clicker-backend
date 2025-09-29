package com.clicker.core.domain.user;

import com.clicker.core.domain.user.mapper.UserMapper;
import com.clicker.core.domain.user.models.DefaultUserDetails;
import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.wallets.WalletsService;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.exception.IncorrectPassword;
import com.clicker.core.exception.not_found.UserNotFoundByEmailException;
import com.clicker.core.exception.not_found.UserNotFoundByIdException;
import com.clicker.core.exception.not_found.UserNotFoundByUsernameException;
import com.clicker.core.security.core.models.authority.AuthorityService;
import com.clicker.core.security.core.models.authority.models.Authority;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final WalletsService walletsService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return new DefaultUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority)
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                userEntity.isExpired(),
                userEntity.isLocked());
    }

    @Override
    @Transactional
    public void save(SystemUserDto systemUserDto) {
        UserEntity savedUserEntity = userRepository.save(userMapper.toEntity(systemUserDto));

        AuthorityEntity authorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);
        savedUserEntity.getAuthorities().add(authorityEntity);

        userRepository.save(savedUserEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public UserResponseDto update(SystemUserUpdateDto systemUserUpdateDto) {
        UserEntity userEntity = userRepository.findById(systemUserUpdateDto.getId()).orElseThrow(
                UserNotFoundByIdException::new
        );
        userMapper.updateEntityFromDto(systemUserUpdateDto, userEntity);
        userEntity.setAuthorities(authorityService.toAuthorityEntityList(systemUserUpdateDto.getAuthorities()));
        walletsService.updateUserWallets(userEntity.getId(), systemUserUpdateDto.getWallets());
        return userMapper.toResponseDto(userRepository.save(userEntity));
    }

    @Transactional
    @Override
    public void updatePasswordByEmail(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void confirmUserByEmail(String email) {
        UserEntity userEntity = userRepository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        AuthorityEntity newAuthorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);

        List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
        authorityEntities.removeIf(auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER));

        if(!authorityEntities.contains(newAuthorityEntity)) {
            authorityEntities.add(newAuthorityEntity);
        }

        userEntity.setAuthorities(authorityEntities);
        userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<WalletResponseDto> findWalletsById(UUID id) {
        return walletsService.findAllWithCurrencyByUserId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto findWithWalletsById(UUID id){
        UserEntity userEntity = userRepository.findWithWalletsById(id).orElseThrow(UserNotFoundByIdException::new);
        return userMapper.toResponseDto(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findEntityById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemUserDto systemFindById(UUID id) {
        UserEntity userEntity = userRepository.findWithAuthorityById(id).orElseThrow(UserNotFoundByEmailException::new);
        return userMapper.toSystemDto(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemUserDto systemFindByEmail(String email) {
        UserEntity userEntity = userRepository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return userMapper.toSystemDto(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public SystemUserDto systemFindByUsername(String username) {
        UserEntity userEntity = userRepository.findWithAuthorityByUsername(username).orElseThrow(UserNotFoundByUsernameException::new);
        return userMapper.toSystemDto(userEntity);
    }

    @Cacheable(
            value = "users",
            key = "'pn' + #pageRequest.pageNumber + ':' + 'ps' + #pageRequest.pageSize + ':' + " +
                    "T(org.springframework.util.DigestUtils).md5DigestAsHex(#filter.toString().getBytes())",
            condition = "#pageRequest.pageNumber >= 0 && #pageRequest.pageNumber <= 2"
    )
    @Transactional(readOnly = true)
    @Override
    public PagedDataDto<PublicUserResponseDto> findAll(UserFilterDto filter, PageRequest pageRequest) {
        Page<UserEntity> page = userRepository.findAll(pageRequest);
        return PagedDataDto.toPagedDataDto(
                userMapper.toPublicResponseDto(page.getContent()),
                page.getTotalElements()
        );
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByPassword(UUID id, String password) throws BadCredentialsException, IncorrectPassword {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            userRepository.deleteById(id);
            return;
        }
        throw new IncorrectPassword();
    }

    @Override
    public SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto) {
        return userMapper.toSystemUpdateDto(userUpdateDto);
    }
}