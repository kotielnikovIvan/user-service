package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.mybatis.mappers.UserMapper;
import org.apache.ibatis.session.SqlSessionManager;

import javax.inject.Inject;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private UserMapper userMapper;

    @Inject
    public UserDaoImpl(SqlSessionManager sessionManager) {
        this.userMapper = sessionManager.getMapper(UserMapper.class);
    }

    public UserDto save(UserDto userDto) {
        userMapper.save(userDto);
        return userDto;
    }

    public UserDto update(UserDto userDto) {
        userMapper.update(userDto);
        return userDto;
    }

    public Optional<UserDto> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public Optional<UserDto> findByLink(String link) {
        return userMapper.findByLink(link);
    }
}
