package com.fitgoal.dao;

import com.fitgoal.dao.domain.UserDto;
import java.util.List;

public interface UserDao {
    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);

    void delete(UserDto userDto);

    UserDto findByEmail(String email);

    UserDto findByLink(String link);
}
