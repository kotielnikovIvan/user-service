package com.fitgoal.service;

import com.fitgoal.api.UserService;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;

import javax.inject.Inject;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void notifyUser(String email) {

        userDao.findByEmail(email)
                .map(userDto -> {
//                    Send email to notification-service;
                    return userDto;
                }).orElseThrow(() -> new UserNotFoundException("User " + email + " not found"));

    }

    public void resetPassword(String link, String newPassword) {

        userDao.findByLink(link)
                .map(userDto -> {
                    userDto.setPassword(newPassword);
                    userDto.setLink(UUID.randomUUID().toString());
                    return userDao.update(userDto);
                }).orElseThrow(() -> new UserNotFoundException("Link " + link + " already invalid"));
    }
}

