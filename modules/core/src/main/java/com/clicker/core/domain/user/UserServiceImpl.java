package com.clicker.core.domain.user;

import com.clicker.core.domain.user.mapper.UserMapper;
import com.clicker.core.domain.user.models.DefaultUserDetails;
import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.domain.user.models.entity.UserEntity;
import com.clicker.core.domain.user.repository.UserRepository;
import com.clicker.core.domain.wallets.WalletsService;
import com.clicker.core.domain.wallets.models.dto.WalletResponseDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import com.clicker.core.exception.ExceptionUtils;
import com.clicker.core.exception.IncorrectPassword;
import com.clicker.core.exception.not_found.UserNotFoundByEmailException;
import com.clicker.core.exception.not_found.UserNotFoundByIdException;
import com.clicker.core.security.core.models.authority.AuthorityService;
import com.clicker.core.security.core.models.authority.models.Authority;
import com.clicker.core.security.core.models.authority.models.AuthorityEntity;
import com.clicker.core.shared.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
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

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final AuthorityService authorityService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final WalletsService walletsService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = repository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return new DefaultUserDetails(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority)
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                entity.isExpired(),
                entity.isLocked());
    }

    @Transactional
    @Override
    public void save(ConfirmedUserDto dto) {
        UserEntity entity = repository.save(mapper.toEntity(dto));
        AuthorityEntity authorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);
        entity.getAuthorities().add(authorityEntity);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    @Override
    public UserResponseDto update(UserUpdateDto dto) {
        try {
            UserEntity entity = repository.findById(dto.id()).orElseThrow(UserNotFoundByIdException::new);
            mapper.updateEntityFromDto(dto, entity);
            Map<Long, WalletUpdateDto> walletDtos = dto.wallets().stream()
                    .collect(Collectors.toMap(WalletUpdateDto::getId, Function.identity()));
            walletsService.updateBatch(walletDtos, entity.getWallets());
            entity.setAuthorities(authorityService.toAuthorityEntitySet(dto.authorities()));
            return mapper.toResponseDto(repository.save(entity));
        } catch(ConstraintViolationException e) {
            log.error("Error when updating wallet, id={}", dto.id(), e);
            throw ExceptionUtils.resolveCurrencyIdError(e);
        }
    }

    @Transactional
    @Override
    public void updatePasswordByEmail(String email, String password) {
        UserEntity entity = repository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        entity.setPassword(passwordEncoder.encode(password));
        repository.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void confirmUserByEmail(String email) {
        UserEntity entity = repository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        AuthorityEntity newAuthorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);
        entity.getAuthorities().removeIf(auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER));
        entity.getAuthorities().add(newAuthorityEntity);
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<WalletResponseDto> findWalletsById(UUID id) {
        return walletsService.findAllWithCurrencyByUserId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto findWithWalletsById(UUID id) {
        return repository.findWithWalletsById(id)
                .map(mapper::toResponseDto)
                .orElseThrow(UserNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public FullUserDto findFullById(UUID id) {
        return repository.findWithWalletsById(id)
                .map(mapper::toFullDto)
                .orElseThrow(UserNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ShortUserDto> findShortByEmail(String email) {
        return repository.findWithAuthorityByEmail(email)
                .map(mapper::toShortDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ShortUserDto> findShortByUsername(String username) {
        return repository.findWithAuthorityByUsername(username)
                .map(mapper::toShortDto);
    }

    @Cacheable(
            value = "users",
            key = "'pn' + #pageRequest.pageNumber + ':' + 'ps' + #pageRequest.pageSize + ':' + " +
                    "T(org.springframework.util.DigestUtils).md5DigestAsHex(#filter.toString().getBytes())",
            condition = "#pageRequest.pageNumber >= 0 && #pageRequest.pageNumber <= 2"
    )
    @Transactional(readOnly = true)
    @Override
    public PageDto<PublicUserResponseDto> findAll(DefaultUserFilter filter, PageRequest pageRequest) {
        Page<UserEntity> page = repository.findAll(pageRequest);
        return PageDto.of(
                mapper.toPublicResponseDto(page.getContent()),
                page.getTotalElements()
        );
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByPassword(UUID id, String password) throws BadCredentialsException, IncorrectPassword {
        UserEntity entity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        if (passwordEncoder.matches(password, entity.getPassword())) {
            repository.deleteById(id);
            return;
        }
        throw new IncorrectPassword();
    }
}