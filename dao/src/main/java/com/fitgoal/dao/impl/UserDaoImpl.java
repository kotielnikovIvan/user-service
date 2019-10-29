package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.mybatis.config.MyBatisConfig;
import com.fitgoal.dao.mybatis.mappers.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;


public class UserDaoImpl implements UserDao {

    public UserDto getById(Long id) throws SqlSessionException {
        UserDto user = null;
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getById(id);
            session.commit();
        }
        return user;
    }

    public UserDto save(UserDto userDto) {
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.save(userDto);
            session.commit();
        }
        return userDto;
    }

    public UserDto update(UserDto userDto) {
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(userDto);
            session.commit();
        }
        return userDto;
    }

    public UserDto findByEmail(String email) throws SqlSessionException {
        UserDto user = null;
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.findByEmail(email);
            session.commit();
        }
        return user;
    }


    public UserDto findByLink(String link) throws SqlSessionException {
        UserDto user = null;
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.findByLink(link);
            session.commit();
        }
        return user;
    }
}
