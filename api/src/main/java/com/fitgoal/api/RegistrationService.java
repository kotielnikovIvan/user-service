package com.fitgoal.api;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;

public interface RegistrationService {

    void register(UserRegistrationData user);

    User activateUser(String link);
}
