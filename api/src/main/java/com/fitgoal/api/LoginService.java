package com.fitgoal.api;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;

public interface LoginService {

    User login(UserLoginData user);
}