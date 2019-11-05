package com.fitgoal.service;

import com.fitgoal.api.ResetPassword;
import com.fitgoal.api.domain.UserEmailData;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;

import javax.inject.Inject;
import java.util.UUID;

public class ResetPasswordService implements ResetPassword {

    private UserDao userDao;

    @Inject
    public ResetPasswordService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void sendEmailForResetPassword(UserEmailData email) {

        userDao.findByEmail(email.getEmail())
                .map(userDto -> {
//                    Send email to notification-service;
                    return userDto;
                })
                .orElseThrow(() -> new UserNotFoundException("User " + email + " not found"));
    }

    public void resetPassword(String link, String newPassword) {

        userDao.findByLink(link)
                .map(userDto -> updateUser(newPassword, userDto))
                .orElseThrow(() -> new UserNotFoundException("Link " + link + " invalid"));
    }

    private UserDto updateUser(String newPassword, UserDto userDto) {
        userDto.setPassword(newPassword);
        userDto.setLink(UUID.randomUUID().toString());
        return userDao.update(userDto);
    }
}

