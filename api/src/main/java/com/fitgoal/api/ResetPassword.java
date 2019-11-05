package com.fitgoal.api;

import com.fitgoal.api.domain.UserEmailData;

public interface ResetPassword {

    void sendEmailForResetPassword(UserEmailData email);

    void resetPassword(String verificationLink, String newPassword);
}
