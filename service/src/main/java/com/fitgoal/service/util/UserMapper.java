package com.fitgoal.service.util;

import com.fitgoal.api.domain.User;
import com.fitgoal.dao.domain.UserDto;

public class UserMapper implements SimpleMapper<User, UserDto> {

    public UserDto convertApiEntityToDtoEntity(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getPassword(), user.getLink(), user.isActive());
    }

    public User convertDtoEntityToApiEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.getPassword(), userDto.getLink(), userDto.isActive());
    }
}