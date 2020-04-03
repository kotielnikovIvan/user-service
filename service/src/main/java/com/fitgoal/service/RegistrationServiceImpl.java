package com.fitgoal.service;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.service.kafka.model.UserEvent;
import com.fitgoal.service.kafka.producer.EventProducer;
import com.fitgoal.service.util.UserConverter;

import javax.inject.Inject;
import java.util.UUID;

public class RegistrationServiceImpl implements RegistrationService {

    private final UserDao userDao;
    private final EventProducer producer;

    @Inject
    public RegistrationServiceImpl(UserDao userDao, EventProducer producer) {
        this.userDao = userDao;
        this.producer = producer;
    }

    public void register(UserRegistrationData userRegistrationData) {
        String email = userRegistrationData.getEmail();

        userDao.findByEmail(email)
                .ifPresent(userDto -> {
                    throw new UserAlreadyExistException(email);
                });
        UserDto userDto = createUser(userRegistrationData);
        UserEvent userEvent = buildUserEvent(userDto);
        producer.sendMessage(String.valueOf(userDto.getId()), userEvent.toString());
    }

    private UserEvent buildUserEvent(UserDto userDto) {
        return UserEvent.builder()
                    .email(userDto.getEmail())
                    .active(userDto.isActive())
                    .build();
    }

    private UserDto createUser(UserRegistrationData userRegistrationData) {
        UserDto user = UserDto.builder()
                .email(userRegistrationData.getEmail())
                .password(userRegistrationData.getPassword())
                .link(UUID.randomUUID().toString())
                .build();
        userDao.save(user);
        return user;
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

