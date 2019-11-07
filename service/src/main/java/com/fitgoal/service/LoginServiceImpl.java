
package com.fitgoal.service;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.service.util.UserConverter;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {

    private UserDao userDao;

    @Inject
    public LoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(UserLoginData user) {
        return userDao.findByEmail(user.getEmail())
                .filter(userDto -> userDto.getPassword().equals(user.getPassword()))
                .map(UserConverter::convertDtoEntityToApiEntity)
                .orElseThrow(IncorrectEmailOrPasswordException::new);
    }
}
