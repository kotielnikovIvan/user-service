package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.mybatis.config.MyBatisConfig;
import com.fitgoal.dao.mybatis.mappers.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;

import java.util.Optional;


public class UserDaoImpl implements UserDao {

    public Optional<UserDto> getById(Long id) {
        Optional<UserDto> user = Optional.empty();
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getById(id);
            session.commit();
        }catch(SqlSessionException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    public UserDto save(UserDto userDto) {
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.save(userDto);
            session.commit();
        }catch(SqlSessionException e){
            System.out.println(e.getMessage());
        }
        return userDto;
    }

    public UserDto update(UserDto userDto) {
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(userDto);
            session.commit();
        }catch(SqlSessionException e){
            System.out.println(e.getMessage());
        }
        return userDto;
    }

    public Optional<UserDto> findByEmail(String email) {
        Optional<UserDto> user = Optional.empty();
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.findByEmail(email);
            session.commit();
        }catch(SqlSessionException e){
            System.out.println(e.getMessage());
        }
        return user;
    }


    public Optional<UserDto> findByLink(String link) {
        Optional<UserDto> user = Optional.empty();
        try (SqlSession session = MyBatisConfig.getSessionFactory().openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.findByLink(link);
            session.commit();
        }catch(SqlSessionException e){
            System.out.println(e.getMessage());
        }
        return user;
    }
}
