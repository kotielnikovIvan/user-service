package com.fitgoal.integration;

import java.io.IOException;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;

import com.fitgoal.api.domain.UserLoginData;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.impl.UserDaoImpl;
import com.fitgoal.integration.util.TestHelper;
import com.fitgoal.web.UserServiceApplication;
import com.fitgoal.web.config.UserServiceConfiguration;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AbstractIntegrationTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("test_db")
            .withInitScript("scripts/initScriptMySql.sql");

    protected final DropwizardAppExtension<UserServiceConfiguration> rule = new DropwizardAppExtension<>(
            UserServiceApplication.class,
            "../config.yml",
            ConfigOverride.config("database.user", mySQLContainer.getUsername()),
            ConfigOverride.config("database.password", mySQLContainer.getPassword()),
            ConfigOverride.config("database.url", mySQLContainer.getJdbcUrl()));

    protected static final String RESOURCE_PATH = "/login";
    protected UserDao dao;
    private TestHelper testHelper = new TestHelper(mySQLContainer);

    @Before
    public void setUp() throws Exception {
        rule.before();
        SqlSessionManager sessionManager = SqlSessionManager.newInstance(getSqlSessionFactory());
        dao = new UserDaoImpl(sessionManager);
    }

    @After
    public void cleanUp() {
        testHelper.deleteAll("users");
    }

   /* @Test
    public void loginUser_whenUserCredentialsCorrect_expect200ErrorCode() {

        UserLoginData userLoginData = createUserLoginData();
        dao.save(createUserDto());

        Response response = rule.client().target("http://localhost:" + rule.getLocalPort() + RESOURCE_PATH)
                .request()
                .method("POST", Entity.json(userLoginData));

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
    }*/

    private static SqlSessionFactory getSqlSessionFactory() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("url", mySQLContainer.getJdbcUrl());
        properties.setProperty("username", mySQLContainer.getUsername());
        properties.setProperty("password", mySQLContainer.getPassword());

        return new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis/mybatis-config.xml"), properties);
    }

    protected static UserLoginData createUserLoginData() {
        return UserLoginData.builder()
                .email("test@gmail.com")
                .password("testPassword")
                .build();
    }

    protected static UserDto createUserDto() {
        return UserDto.builder()
                .email("test@gmail.com")
                .password("testPassword")
                .link("testLink")
                .active(false)
                .build();
    }
}
