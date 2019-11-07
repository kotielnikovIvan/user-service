package com.fitgoal.service.util;

import com.fitgoal.api.domain.User;
import com.fitgoal.dao.domain.UserDto;

public class UserConverter {

    public static User convertDtoEntityToApiEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .link(userDto.getLink())
                .active(userDto.isActive())
                .build();
    }
}