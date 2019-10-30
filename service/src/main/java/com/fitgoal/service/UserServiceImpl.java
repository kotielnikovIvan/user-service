package com.fitgoal.service;

import com.fitgoal.api.UserService;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;

import javax.inject.Inject;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void notifyUser(String email) {
        /*UserDto userDto = userDao.findByEmail(email);
        if (userDto == null) {
//            TODO: Handle User not found exception;
        }*/
//        Send email and link to notification service

        userDao.findByEmail(email)
                .map(userDto -> {
//                    Send email to notification-service;
                    return userDto;
//                    TODO: Replace with UserNotFoundException();
                }).orElseThrow(RuntimeException::new);

    }

/*    @Override
    public String verifyUser(String link) {
        UserDto userDto = userDao.findByLink(link);
        if(userDto == null){
//            TODO: Handle User not found exception;
        }
        return link;
    }*/

    public void resetPassword(String link, String newPassword) {

        userDao.findByLink(link)
                .map(userDto -> {
                    userDto.setPassword(newPassword);
                    userDto.setLink(UUID.randomUUID().toString());
                    return userDao.update(userDto);
//                    TODO: Replace with InvalidVerificationLink();
                }).orElseThrow(RuntimeException::new);

    }
}

