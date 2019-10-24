package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDAO<UserDto> implements UserDao {

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @UnitOfWork
    public UserDto findById(Long id) {
        return (UserDto) get(id);
    }

    public UserDto create(UserDto user) {
        return (UserDto) persist(user);
    }

    public UserDto update(UserDto entity) {
        return null;
    }

    public void delete(UserDto entity) {
    }

    public List<UserDto> findAll() {
        return list((Criteria) namedQuery("com.fitgoal.User.findAll"));
    }
}
