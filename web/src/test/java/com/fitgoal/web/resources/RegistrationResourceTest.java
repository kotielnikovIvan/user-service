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
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.fitgoal.web.util.WebTestHelper.createUser;
import static com.fitgoal.web.util.WebTestHelper.createUserRegistrationData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(DropwizardExtensionsSupport.class)
public class RegistrationResourceTest {

    private final String resourcePath = "/registration";
    private final RegistrationService registrationService = mock(RegistrationService.class);
    public final ResourceExtension resourceExtension = ResourceExtension.builder()
            .addResource(new RegistrationResource(registrationService))
            .addProvider(UserAlreadyExistExceptionMapper.class)
            .addProvider(UserNotFoundExceptionMapper.class)
            .build();

    @Before
    public void setUp() throws Throwable {
        resourceExtension.before();
    }

    @Test
    public void registerUser_whenUserNotExists_expect204StatusCode() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();

        Response response = resourceExtension.target(resourcePath)
                .request()
                .method("PUT", Entity.json(testUserRegistrationData));

        doNothing().when(registrationService).register(any(UserRegistrationData.class));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(204);
        verify(registrationService).register(any(UserRegistrationData.class));
    }

    @Test
    public void registerUser_whenUserExists_expect400StatusCode() {
        UserRegistrationData testUserRegistrationData = createUserRegistrationData();

        doThrow(UserAlreadyExistException.class)
                .when(registrationService).register(any(UserRegistrationData.class));

        Response response = resourceExtension.target(resourcePath)
                .request()
                .method("PUT", Entity.json(testUserRegistrationData));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
        verify(registrationService).register(any(UserRegistrationData.class));
    }

    @Test
    public void verifyUser_whenUserLinkValid_assertContent() {
        User user = createUser();
        String testLink = UUID.randomUUID().toString();

        when(registrationService.activateUser(testLink))
                .thenReturn(user);

        User actualUser = resourceExtension.target(resourcePath)
                .path("/verify/" + testLink)
                .request()
                .method("POST", User.class);

        assertThat(actualUser).isNotNull();
        assertThat(actualUser).isEqualTo(user);
        verify(registrationService).activateUser(testLink);
    }

    @Test
    public void verifyUser_whenUserLinkNotValid_expect404StatusCode() {
        String testLink = UUID.randomUUID().toString();

        when(registrationService.activateUser(testLink))
                .thenThrow(UserNotFoundException.class);

        Response response = resourceExtension.target(resourcePath)
                .path("/verify/" + testLink)
                .request()
                .method("POST");

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
        verify(registrationService).activateUser(testLink);
    }
}