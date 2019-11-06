package com.fitgoal.service;

import com.fitgoal.api.ResetPasswordService;
import com.fitgoal.api.domain.UserEmailData;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;

import javax.inject.Inject;
import java.util.UUID;

public class ResetPasswordServiceImpl implements ResetPasswordService {

    private UserDao userDao;

    @Inject
    public ResetPasswordServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void sendEmailForResetPassword(UserEmailData email) {

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

