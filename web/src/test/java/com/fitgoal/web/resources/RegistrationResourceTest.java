package com.fitgoal.web.resources;

import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.api.exceptions.UserAlreadyExistException;
import com.fitgoal.api.exceptions.UserNotFoundException;
import com.fitgoal.web.exceptionmapper.UserAlreadyExistExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserNotFoundExceptionMapper;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.fitgoal.web.resources.util.TestHelper.createUser;
import static com.fitgoal.web.resources.util.TestHelper.createUserRegistrationData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(DropwizardExtensionsSupport.class)
public class RegistrationResourceTest {

    private static final String RESOURCE_PATH = "/registration";
    private static final RegistrationService REGISTRATION_SERVICE = mock(RegistrationService.class);
    public static final ResourceExtension RESOURCE = ResourceExtension.builder()
            .addResource(new RegistrationResource(REGISTRATION_SERVICE))
            .addProvider(UserAlreadyExistExceptionMapper.class)
            .addProvider(UserNotFoundExceptionMapper.class)
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
        reset(REGISTRATION_SERVICE);
    }

    @Test
    public void whenRegisterNewUser_thenReturnNoContentStatusCode() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();

        Response response = RESOURCE.target(RESOURCE_PATH)
                .request()
                .method("PUT", Entity.json(testUserRegistrationData));

        doNothing().when(REGISTRATION_SERVICE).register(any(UserRegistrationData.class));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(204);
        verify(REGISTRATION_SERVICE, times(1))
                .register(any(UserRegistrationData.class));
    }

    @Test
    public void whenRegisterExistingUser_thenReturnBadRequestStatusCode() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();

        doThrow(UserAlreadyExistException.class)
                .when(REGISTRATION_SERVICE).register(any(UserRegistrationData.class));

        Response response = RESOURCE.target(RESOURCE_PATH)
                .request()
                .method("PUT", Entity.json(testUserRegistrationData));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
        verify(REGISTRATION_SERVICE, times(1))
                .register(any(UserRegistrationData.class));
    }

    @Test
    public void whenActivateUserByValidLink_thenReturnUser() {
        User user = createUser();
        String testLink = UUID.randomUUID().toString();

        when(REGISTRATION_SERVICE.activateUser(testLink))
                .thenReturn(user);

        User actualUser = RESOURCE.target(RESOURCE_PATH)
                .path("/verify/" + testLink)
                .request()
                .method("POST", User.class);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(user);
        verify(REGISTRATION_SERVICE, times(1)).activateUser(testLink);
    }

    @Test
    public void whenActivateUserByNotValidLink_thenReturnBadRequestStatusCode() {
        String testLink = UUID.randomUUID().toString();

        when(REGISTRATION_SERVICE.activateUser(testLink))
                .thenThrow(UserNotFoundException.class);

        Response response = RESOURCE.target(RESOURCE_PATH)
                .path("/verify/" + testLink)
                .request()
                .method("POST");

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
        verify(REGISTRATION_SERVICE, times(1)).activateUser(testLink);
    }
}