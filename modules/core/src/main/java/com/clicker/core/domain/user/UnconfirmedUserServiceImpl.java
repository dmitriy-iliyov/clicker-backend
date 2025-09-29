package com.clicker.core.domain.user;

import com.clicker.core.domain.user.mapper.UserMapper;
import com.clicker.core.domain.user.models.dto.SystemUserDto;
import com.clicker.core.domain.user.models.dto.UserRegistrationDto;
import com.clicker.core.domain.user.models.entity.UnconfirmedUserEntity;
import com.clicker.core.exception.not_found.UnconfirmedUserNotFoundByEmailException;
import com.clicker.core.exception.not_found.UnconfirmedUserNotFoundByUsernameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnconfirmedUserServiceImpl implements UnconfirmedUserService {

    private final UnconfirmedUserRepository unconfirmedUserRepository;
    private final UserMapper userMapper;


    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
        unconfirmedUserRepository.save(userMapper.toUnconfirmedEntity(userRegistrationDto));
    }

    @Override
    public boolean existsByEmail(String email) {
        return unconfirmedUserRepository.existsById(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return unconfirmedUserRepository.existsByUsername(username);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email) {
        UnconfirmedUserEntity unconfirmedUserEntity = unconfirmedUserRepository.findById(email).orElseThrow(
                UnconfirmedUserNotFoundByEmailException::new
        );
        return userMapper.toSystemDto(unconfirmedUserEntity);
    }

    @Override
    public SystemUserDto systemFindByUsername(String username) {
        UnconfirmedUserEntity unconfirmedUserEntity = unconfirmedUserRepository.findByUsername(username).orElseThrow(
                UnconfirmedUserNotFoundByUsernameException::new
        );
        return userMapper.toSystemDto(unconfirmedUserEntity);
    }

    @Override
    public void deleteByEmail(String email) {
        unconfirmedUserRepository.deleteById(email);
    }
}
