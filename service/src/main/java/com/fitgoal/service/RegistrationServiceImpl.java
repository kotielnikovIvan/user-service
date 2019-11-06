package com.fitgoal.service;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.mongo.MongoUserDao;
import com.fitgoal.service.util.UserConverter;

import javax.inject.Inject;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {

    private final MongoUserDao mongoUserDao;

    @Inject
    public RegistrationServiceImpl(MongoUserDao mongoUserDao) {
        this.mongoUserDao = mongoUserDao;
    }

    public void register(UserRegistrationData userRegistrationData) {
        String email = userRegistrationData.getEmail();

        mongoUserDao.findByEmail(email)
                .ifPresent(userDto -> {
                    throw new UserAlreadyExistException(email);
                });
        createUser(userRegistrationData, email);
    }

    private void createUser(UserRegistrationData userRegistrationData, String email) {
        UserDto user = UserDto.builder()
                .email(email)
                .password(userRegistrationData.getPassword())
                .link(UUID.randomUUID().toString())
                .build();
        mongoUserDao.save(user);
    }

    @Override
    public User activateUser(String link) {

        return mongoUserDao.findByLink(link)
                .map(this::updateUser)
                .map(UserConverter::convertDtoEntityToApiEntity)
                .orElseThrow(() -> new UserNotFoundException("Link " + link + " invalid"));
    }

    private UserDto updateUser(UserDto userDto) {
        userDto.setActive(true);
        userDto.setLink(UUID.randomUUID().toString());
        return mongoUserDao.update(userDto);
    }
}

