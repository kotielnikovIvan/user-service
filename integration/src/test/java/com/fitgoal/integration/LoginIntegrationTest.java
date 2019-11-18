package com.fitgoal.integration;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.dao.domain.UserDto;

public class LoginIntegrationTest extends AbstractIntegrationTest {

    private final String resourcePath = APP_URL + "/login";

    @Test
    public void loginUser_whenUserCredentialsCorrect_expect200StatusCode() {
        UserLoginData userLoginData = getUserLoginData();

        Response response = getPostResponse(userLoginData);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void loginUser_whenUserEmailNotCorrect_expect400StatusCode() {
        UserLoginData userLoginData = getUserLoginData();
        userLoginData.setEmail("wrongEmail@gmail.com");

        Response response = getPostResponse(userLoginData);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    public void loginUser_whenUserPasswordNotCorrect_expect400StatusCode() {
        UserLoginData userLoginData = getUserLoginData();
        userLoginData.setPassword("wrongPassword");

        Response response = getPostResponse(userLoginData);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
    }

    private Response getPostResponse(UserLoginData userLoginData) {
        return appExtension.client().target(resourcePath)
                .request()
                .method("POST", Entity.json(userLoginData));
    }

    private UserLoginData getUserLoginData() {
        UserDto userDto = saveUserToDB();
        return UserLoginData.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
