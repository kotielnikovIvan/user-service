package com.fitgoal.web.resources;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static com.fitgoal.web.resources.util.TestHelper.createUser;
import static com.fitgoal.web.resources.util.TestHelper.createUserLoginData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(DropwizardExtensionsSupport.class)
public class LoginResourceTest {

    private String resourcePath = "/login";
    private LoginService loginService = mock(LoginService.class);
    public ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new LoginResource(loginService))
            .addProvider(IncorrectEmailOrPasswordExceptionMapper.class)
            .build();

    @Before
    public void setUp() throws Throwable {
        resourceExtension.before();
    }

    @After
    public void tearDown() throws Throwable {
        resourceExtension.after();
    }

    @Test
    public void whenLoginUser_thenReturnUserData() {
        UserLoginData testUserLoginData = createUserLoginData();
        User user = createUser();

        when(loginService.login(any(UserLoginData.class))).thenReturn(user);

        User actualUser = resourceExtension.target(resourcePath)
                .request()
                .post(Entity.json(testUserLoginData), User.class);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(user);
        verify(loginService, times(1)).login(any(UserLoginData.class));
    }

    @Test
    public void whenLoginUserWithBadCredentials_thenReturnBadRequestStatusCode() {
        UserLoginData testUserLoginData = createUserLoginData();

        when(loginService.login(any(UserLoginData.class)))
                .thenThrow(IncorrectEmailOrPasswordException.class);

        Response response = resourceExtension.target(resourcePath)
                .request()
                .method("POST", Entity.json(testUserLoginData));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
        verify(loginService, times(1)).login(any(UserLoginData.class));
    }
}