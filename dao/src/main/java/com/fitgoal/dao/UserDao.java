package com.fitgoal.dao;

import com.fitgoal.dao.domain.UserDto;
import java.util.List;

public interface UserDao {
    UserDto findById(Long var1);

    List<UserDto> findAll();

    UserDto create(UserDto var1);

    UserDto update(UserDto var1);

    void delete(UserDto var1);
}
