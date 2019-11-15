package com.fitgoal.integration;

import com.fitgoal.api.domain.UserLoginData;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static com.fitgoal.integration.AbstractIntegrationTest.createUserDto;
import static com.fitgoal.integration.AbstractIntegrationTest.createUserLoginData;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void loginUser_whenUserCredentialsCorrect_expect200ErrorCode() {

        UserLoginData userLoginData = createUserLoginData();
        dao.save(createUserDto());

        Response response = rule.client().target("http://localhost:" + rule.getLocalPort() + RESOURCE_PATH)
                .request()
                .method("POST", Entity.json(userLoginData));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
    }


}
