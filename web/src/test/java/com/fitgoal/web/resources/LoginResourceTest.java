package com.fitgoal.web.resources;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.fitgoal.web.resources.util.TestHelper.createUser;
import static com.fitgoal.web.resources.util.TestHelper.createUserLoginData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @Test
    public void whenLoginUser_thenReturnUserData() {
        UserLoginData testUserLoginData = createUserLoginData();
        User user = createUser();

        when(LOGIN_SERVICE.login(testUserLoginData)).thenReturn(user);

        Response response = RESOURCE.target(RESOURCE_PATH)
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(LOGIN_SERVICE, times(1)).login(any(UserLoginData.class));
    }
}