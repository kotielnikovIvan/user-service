package com.fitgoal.api;

public interface UserService {

    void notifyUser(String email);

    void resetPassword(String verificationLink, String newPassword);
}
