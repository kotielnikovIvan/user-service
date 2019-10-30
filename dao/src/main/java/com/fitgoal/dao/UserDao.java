package com.fitgoal.dao;

import com.fitgoal.dao.domain.UserDto;

import java.util.Optional;

public interface UserDao {

    Optional<UserDto> getById(Long id);

//    List<UserDto> findAll();

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

//    void delete(UserDto userDto);

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByLink(String link);
}
