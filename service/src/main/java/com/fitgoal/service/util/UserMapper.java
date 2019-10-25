package com.fitgoal.service.util;

import com.fitgoal.api.domain.User;
import com.fitgoal.dao.domain.UserDto;

public class UserMapper implements SimpleMapper<User, UserDto> {

    public UserDto convertApiEntityToDtoEntity(User user) {
        return new UserDto(user.getEmail(), user.getPassword(), user.isActive());
    }

    public User convertDtoEntityToApiEntity(UserDto userDto) {
        return new User(userDto.getId(), userDto.getEmail(), userDto.isActive());
    }
}