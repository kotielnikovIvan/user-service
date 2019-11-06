
package com.fitgoal.service;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.mongo.MongoUserDao;
import com.fitgoal.service.util.UserConverter;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {

    private MongoUserDao mongoUserDao;

    @Inject
    public LoginServiceImpl(MongoUserDao mongoUserDao) {
        this.mongoUserDao = mongoUserDao;
    }

    public User login(UserLoginData user) {
        return mongoUserDao.findByEmail(user.getEmail())
                .filter(userDto -> userDto.getPassword().equals(user.getPassword()))
                .map(UserConverter::convertDtoEntityToApiEntity)
                .orElseThrow(IncorrectEmailOrPasswordException::new);
    }
}
