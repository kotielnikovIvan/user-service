package com.fitgoal.api;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;

public interface LoginService {

    User login(UserAccessData user);
}