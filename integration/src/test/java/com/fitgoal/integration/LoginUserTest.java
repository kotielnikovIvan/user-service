package com.fitgoal.integration;

import com.fitgoal.web.UserServiceApplication;
import com.fitgoal.web.config.UserServiceConfiguration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;

public class LoginUserTest {

    public static final DropwizardAppExtension<UserServiceConfiguration> RULE =
            new DropwizardAppExtension<>(UserServiceApplication.class,
                    ResourceHelpers.resourceFilePath("config.yml"));

}
