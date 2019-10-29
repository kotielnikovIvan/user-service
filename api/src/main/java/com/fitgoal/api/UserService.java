package com.fitgoal.api;

public interface UserService {

    void notifyUser(String email);

//    String verifyUser(String link);

    void resetPassword(String verificationLink, String newPassword);
}
