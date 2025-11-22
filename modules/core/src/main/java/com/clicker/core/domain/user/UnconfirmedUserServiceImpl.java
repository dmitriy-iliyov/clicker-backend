package com.clicker.core.domain.user;

import com.clicker.core.domain.user.mapper.UserMapper;
import com.clicker.core.domain.user.models.dto.UserDto;
import com.clicker.core.domain.user.models.dto.UserRegistrationRequest;
import com.clicker.core.domain.user.models.entity.UnconfirmedUserEntity;
import com.clicker.core.domain.user.repository.UnconfirmedUserRepository;
import com.clicker.core.exception.not_found.UnconfirmedUserNotFoundByEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnconfirmedUserServiceImpl implements UnconfirmedUserService {

    private final UnconfirmedUserRepository repository;
    private final UserMapper mapper;

    @Override
    public void save(UserRegistrationRequest userRegistrationRequest) {
        repository.save(mapper.toUnconfirmedEntity(userRegistrationRequest));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsById(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public UserDto findByEmail(String email) {
        UnconfirmedUserEntity entity = repository.findById(email).orElseThrow(
                UnconfirmedUserNotFoundByEmailException::new
        );
        return mapper.toDto(entity);
    }

    @Override
    public Optional<UserDto> systemFindByEmail(String email) {
        return repository.findById(email)
                .map(mapper::toDto);
    }

    @Override
    public Optional<UserDto> systemFindByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toDto);
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteById(email);
    }
}
