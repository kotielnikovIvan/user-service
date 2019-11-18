package com.fitgoal.integration;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserRegistrationData;
import com.fitgoal.dao.domain.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationIntegrationTest extends AbstractIntegrationTest {

    private final String resourcePath = APP_URL + "/registration";

    @Test
    public void registerUser_whenUserNotExists_expect204StatusCode() {
        UserRegistrationData userRegistrationData = createUserRegistrationData();

        Response response = getPutResponse(userRegistrationData);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(204);
    }

    @Test
    public void registerUser_whenUserExists_expect400StatusCode() {
        UserRegistrationData userRegistrationData = getUserRegistrationData();

        Response response = getPutResponse(userRegistrationData);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void activateUser_whenUserLinkNotExpired_expect200StatusCodeAndAssertContent() {
        UserDto testUserDto = saveUserToDB();
        String testLink = testUserDto.getLink();

        Response response = getPostResponse(testLink);
        User actualUser = response.readEntity(User.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getEmail()).isEqualTo(testUserDto.getEmail());
    }

    @Test
    public void activateUser_whenUserLinkExpired_expect404StatusCode() {
        String expiredLink = UUID.randomUUID().toString();
        saveUserToDB();

        Response response = getPostResponse(expiredLink);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    private Response getPutResponse(UserRegistrationData userRegistrationData) {
        return appExtension.client().target(resourcePath)
                .request()
                .method("PUT", Entity.json(userRegistrationData));
    }

    private Response getPostResponse(String testLink) {
        return appExtension.client().target(resourcePath)
                .path("/verify/" + testLink)
                .request()
                .method("POST");
    }

    private UserRegistrationData getUserRegistrationData() {
        UserDto userDto = saveUserToDB();
        return UserRegistrationData.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    private UserRegistrationData createUserRegistrationData() {
        return UserRegistrationData.builder()
                .email("newEmail@gmail.com")
                .password("newPassword")
                .build();
    }

}
