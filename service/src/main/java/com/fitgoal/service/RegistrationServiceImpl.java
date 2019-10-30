package com.fitgoal.service;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.service.util.SimpleConverter;

import javax.inject.Inject;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {

    private final UserDao userDao;
    private final SimpleConverter converter;

    @Inject
    public RegistrationServiceImpl(UserDao userDao, SimpleConverter converter) {
        this.userDao = userDao;
        this.converter = converter;
    }

    public void register(UserAccessData userAccessData) {
        String email = userAccessData.getEmail();

        if (userDao.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistException("User with " + email + " email already exist");
        }
        UserDto user = new UserDto();
        user.setEmail(email);
        user.setPassword(userAccessData.getPassword());
        userDao.save(user);

        /*userDao.findByEmail(userAccessData.getEmail())
                .ifPresentOrElse(userDto -> {
                    throw new UserAlreadyExistException("User with " + email + " email already exist");
                }, () -> {
                    UserDto user = new UserDto();
                    user.setEmail(userAccessData.getEmail());
                    user.setPassword(userAccessData.getPassword());
                    userDao.save(user);
                });*/
    }

    @Override
    public User activateUser(String link) {

        return userDao.findByLink(link)
                .map(userDto -> {
                    userDto.setActive(true);
                    userDto.setLink(UUID.randomUUID().toString());
                    userDao.update(userDto);
                    return (User) converter.convertDtoEntityToApiEntity(userDto);
                })
                .orElseThrow(() -> new UserNotFoundException("Link " + link + " already invalid"));
    }
}

