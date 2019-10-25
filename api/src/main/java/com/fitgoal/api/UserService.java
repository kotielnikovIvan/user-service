package com.fitgoal.api;

import com.fitgoal.api.domain.User;

public interface UserService {

    void notifyUser(String email);

//    String verifyUser(String link);

    void resetPassword(String verificationLink, String newPassword);
}
