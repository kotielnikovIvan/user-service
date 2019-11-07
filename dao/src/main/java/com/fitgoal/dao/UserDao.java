package com.fitgoal.dao;

import com.fitgoal.dao.domain.UserDto;

import java.util.Optional;

public interface UserDao {

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByLink(String link);
}
