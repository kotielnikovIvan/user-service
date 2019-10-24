
package com.fitgoal.service;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.service.util.UserMapper;
import javax.inject.Inject;

public class LoginServiceImpl implements LoginService {

    private UserDao userDao;

    @Inject
    public LoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(Long userId) {
        UserDto userDto = userDao.findById(userId);
        if (userDto == null) {
        }
        UserMapper mapper = new UserMapper();
        return mapper.convertDtoEntityToApiEntity(userDto);
    }
}
