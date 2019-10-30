
package com.fitgoal.service;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.service.util.SimpleConverter;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {

    private UserDao userDao;
    private SimpleConverter converter;

    @Inject
    public LoginServiceImpl(UserDao userDao, SimpleConverter converter) {
        this.userDao = userDao;
        this.converter = converter;
    }

    public User login(UserAccessData user) {
        return userDao.findByEmail(user.getEmail())
                .filter(userDto -> userDto.getPassword().equals(user.getPassword()))
                .map(userDto -> (User) converter.convertDtoEntityToApiEntity(userDto))
                .orElseThrow(() -> new IncorrectEmailOrPasswordException("Incorrect email or password"));
    }
}
