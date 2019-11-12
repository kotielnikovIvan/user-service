package com.fitgoal.web.resources.util;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.dao.domain.UserDto;

public class TestHelper {

    public static UserLoginData createUserLoginData() {
        return UserLoginData.builder()
                .email("test@gmail.com")
                .password("testPass")
                .build();
    }

    public static UserRegistrationData createUserRegistrationData() {
        return UserRegistrationData.builder()
                .email("test@gmail.com")
                .password("testPass")
                .build();
    }

    public static User createUser() {
        return User.builder()
                .id(1L)
                .email("test@gmail.com")
                .link("testLink")
                .active(false)
                .build();
    }
}
