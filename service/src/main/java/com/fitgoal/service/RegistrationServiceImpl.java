package com.fitgoal.service;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.service.util.UserConverter;

import javax.inject.Inject;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {

    private final UserDao userDao;

    @Inject
    public RegistrationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void register(UserRegistrationData userRegistrationData) {
        String email = userRegistrationData.getEmail();

        userDao.findByEmail(email)
                .ifPresent(userDto -> {
                    throw new UserAlreadyExistException(email);
                });
        createUser(userRegistrationData);
    }

    private void createUser(UserRegistrationData userRegistrationData) {
        UserDto user = UserDto.builder()
                .email(userRegistrationData.getEmail())
                .password(userRegistrationData.getPassword())
                .link(UUID.randomUUID().toString())
                .build();
        userDao.save(user);
    }

    @Override
    public User activateUser(String link) {

        return userDao.findByLink(link)
                .map(this::updateUser)
                .map(UserConverter::convertDtoEntityToApiEntity)
                .orElseThrow(() -> new UserNotFoundException("Link " + link + " invalid"));
    }

    private UserDto updateUser(UserDto userDto) {
        userDto.setActive(true);
        userDto.setLink(UUID.randomUUID().toString());
        return userDao.update(userDto);
    }
}

