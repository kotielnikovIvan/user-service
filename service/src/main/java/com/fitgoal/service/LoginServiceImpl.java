
package com.fitgoal.service;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
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
        UserDto userDto = userDao.findByEmail(user.getEmail());
        if (userDto == null || !user.getPassword().equals(userDto.getPassword())) {
//            TODO: Handle incorrect email or password exception;
        }
        return (User) converter.convertDtoEntityToApiEntity(userDto);
    }
}
