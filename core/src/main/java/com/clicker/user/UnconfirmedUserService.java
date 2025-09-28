package com.clicker.user;

import com.example.clicker.user.models.dto.SystemUserDto;
import com.example.clicker.user.models.dto.UserRegistrationDto;

public interface UnconfirmedUserService {

    void save(UserRegistrationDto userRegistrationDto);

    boolean existsByEmail(String email);

    SystemUserDto systemFindByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByUsername(String username);

    SystemUserDto systemFindByUsername(String username);
}
