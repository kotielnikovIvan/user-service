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
    private final SimpleConverter mapper;

    @Inject
    public RegistrationServiceImpl(UserDao userDao, SimpleConverter mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    public void register(UserAccessData userAccessData) {
        UserDto userDto = userDao.findByEmail(userAccessData.getEmail());
        if (userDto != null) {
//            TODO handle UserAlreadyExistsException;
        }
        User user = new User();
        user.setEmail(userAccessData.getEmail());
        user.setPassword(userAccessData.getPassword());
        userDao.save((UserDto) mapper.convertApiEntityToDtoEntity(user));



    }

    @Override
    public User activateUser(String link) {
        UserDto userDto = userDao.findByLink(link);
        if (userDto == null) {
//            TODO handle UserNotFoundException;
        }
        userDto.setActive(true);
        userDto.setLink(UUID.randomUUID().toString());
        userDao.update(userDto);
        return (User) mapper.convertDtoEntityToApiEntity(userDto);
    }
}

