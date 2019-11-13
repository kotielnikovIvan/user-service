package com.fitgoal.web;

import com.fitgoal.web.config.UserServiceConfiguration;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserAlreadyExistExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserNotFoundExceptionMapper;
import com.fitgoal.web.resources.LoginResource;
import com.fitgoal.web.resources.RegistrationResource;
import com.fitgoal.web.resources.ResetPasswordResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceApplicationTest {

    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final UserServiceApplication application = new UserServiceApplication();
    private final UserServiceConfiguration config = new UserServiceConfiguration();

    @Before
    public void setUp() {
        config.setDataSourceFactory(createDataSourceFactory());
    }

    @Test
    public void runApp_whenEnvironmentConfigured_verifyRegisterResources() throws Exception {
        when(environment.jersey()).thenReturn(jersey);

        application.run(config, environment);

        verify(jersey).register(LoginResource.class);
        verify(jersey).register(RegistrationResource.class);
        verify(jersey).register(ResetPasswordResource.class);
        verify(jersey).register(UserNotFoundExceptionMapper.class);
        verify(jersey).register(IncorrectEmailOrPasswordExceptionMapper.class);
        verify(jersey).register(UserAlreadyExistExceptionMapper.class);
    }

    public static DataSourceFactory createDataSourceFactory() {
        DataSourceFactory sourceFactory = new DataSourceFactory();
        sourceFactory.setDriverClass("com.mysql.jdbc.Driver");
        sourceFactory.setUrl("jdbc:mysql://localhost:3306/test_db");
        sourceFactory.setUser("test");
        sourceFactory.setPassword("test");
        return sourceFactory;
    }
}