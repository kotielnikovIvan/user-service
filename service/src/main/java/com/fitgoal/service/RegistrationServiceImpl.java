package com.fitgoal.service;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;
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
        if (userDao.findByEmail(userAccessData.getEmail()).isPresent()) {
//         TODO: Replace with new UserAlreadyExistException();
            throw new RuntimeException();
        }
        UserDto user = new UserDto();
        user.setEmail(userAccessData.getEmail());
        user.setPassword(userAccessData.getPassword());
        userDao.save(user);

        /*userDao.findByEmail(userAccessData.getEmail())
                .ifPresentOrElse(userDto -> {
                    throw new RuntimeException();
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
//               TODO: Replace with custom UserNotFoundException();
                .orElseThrow(RuntimeException::new);
    }
}

