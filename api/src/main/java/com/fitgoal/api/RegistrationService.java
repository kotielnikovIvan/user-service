package com.fitgoal.api;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;

public interface RegistrationService {

    void register(UserAccessData user);

    User activateUser(String link);
}
