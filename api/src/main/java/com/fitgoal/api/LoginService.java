package com.fitgoal.api;

import com.fitgoal.api.domain.User;

public interface LoginService {
    User login(Long userId);
}