package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.internal.CriteriaImpl;

import javax.persistence.Query;

public class UserDaoImpl extends AbstractDAO<UserDto> implements UserDao {

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @UnitOfWork
    public UserDto findById(Long id) {
        return get(id);
    }

    @UnitOfWork
    public UserDto create(UserDto userDto) {
        return persist(userDto);
    }

    @UnitOfWork
    public UserDto update(UserDto userDto) {
        return null;
    }

    @UnitOfWork
    public void delete(UserDto userDto) {
    }

    @Override
    @UnitOfWork
    public UserDto findByEmail(String email) {
        return get(1L);
    }

    @UnitOfWork
    public List<UserDto> findAll() {
        return null;
    }

    @UnitOfWork
    public UserDto findByLink(String link){
        return get(1L);
    }
}
