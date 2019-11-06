package com.fitgoal.dao.mongo;

import com.fitgoal.dao.domain.UserDto;

import java.util.Optional;

public interface MongoUserDao {

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByLink(String link);

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);
}
