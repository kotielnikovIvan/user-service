package com.fitgoal.dao;

import com.fitgoal.dao.domain.UserDto;

public interface UserDao {

    UserDto getById(Long id);

//    List<UserDto> findAll();

    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

//    void delete(UserDto userDto);

    UserDto findByEmail(String email);

    UserDto findByLink(String link);
}
