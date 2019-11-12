package com.fitgoal.web.resources;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static com.fitgoal.web.resources.util.TestHelper.createUser;
import static com.fitgoal.web.resources.util.TestHelper.createUserLoginData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(DropwizardExtensionsSupport.class)
public class LoginResourceTest {

    private static final String RESOURCE_PATH = "/login";
    private static final LoginService LOGIN_SERVICE = mock(LoginService.class);
    public static final ResourceExtension RESOURCE = ResourceExtension.builder()
            .addResource(new LoginResource(LOGIN_SERVICE))
            .addProvider(IncorrectEmailOrPasswordExceptionMapper.class)
            .build();

    @BeforeClass
    public static void setUp() throws Throwable {
        RESOURCE.before();
    }

    @AfterClass
    public static void tearDown() throws Throwable {
        RESOURCE.after();
    }

    @After
    public void tearDownAfterEach() {
        reset(LOGIN_SERVICE);
    }

    @Test
    public void whenLoginUser_thenReturnUserData() {
        UserLoginData testUserLoginData = createUserLoginData();
        User user = createUser();

        when(LOGIN_SERVICE.login(any(UserLoginData.class))).thenReturn(user);

        User actualUser = RESOURCE.target(RESOURCE_PATH)
                .request()
                .post(Entity.json(testUserLoginData), User.class);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(user);
        verify(LOGIN_SERVICE, times(1)).login(any(UserLoginData.class));
    }

    @Test
    public void whenLoginUserWithBadCredentials_thenReturnBadRequestStatusCode() {
        UserLoginData testUserLoginData = createUserLoginData();

        when(LOGIN_SERVICE.login(any(UserLoginData.class)))
                .thenThrow(IncorrectEmailOrPasswordException.class);

        Response response = RESOURCE.target(RESOURCE_PATH)
                .request()
                .method("POST", Entity.json(testUserLoginData));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
        verify(LOGIN_SERVICE, times(1)).login(any(UserLoginData.class));
    }
}