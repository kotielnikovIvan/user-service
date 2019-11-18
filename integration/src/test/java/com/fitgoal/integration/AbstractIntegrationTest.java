package com.fitgoal.integration;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;

import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.impl.UserDaoImpl;
import com.fitgoal.integration.util.IntegrationTestHelper;
import com.fitgoal.web.UserServiceApplication;
import com.fitgoal.web.config.UserServiceConfiguration;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public abstract class AbstractIntegrationTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("test_db")
            .withInitScript("scripts/initScriptMySql.sql");

    protected static DropwizardAppExtension<UserServiceConfiguration> appExtension;
    protected final String APP_URL = "http://localhost:" + appExtension.getLocalPort();

    private UserDao dao;
    private IntegrationTestHelper testHelper = new IntegrationTestHelper(mySQLContainer);

    @BeforeClass
    public static void setUpExtension() throws Exception {
        appExtension = new DropwizardAppExtension<>(
                UserServiceApplication.class,
                "../config.yml",
                ConfigOverride.config("database.user", mySQLContainer.getUsername()),
                ConfigOverride.config("database.password", mySQLContainer.getPassword()),
                ConfigOverride.config("database.url", mySQLContainer.getJdbcUrl()));
        appExtension.before();
    }

    @AfterClass
    public static void tearDown() {
        appExtension.after();
    }

    @Before
    public void setUp() throws Exception {
        SqlSessionManager sessionManager = SqlSessionManager.newInstance(getSqlSessionFactory());
        dao = new UserDaoImpl(sessionManager);
    }

    @After
    public void cleanUp() {
        testHelper.deleteAll("users");
    }

    private static SqlSessionFactory getSqlSessionFactory() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("url", mySQLContainer.getJdbcUrl());
        properties.setProperty("username", mySQLContainer.getUsername());
        properties.setProperty("password", mySQLContainer.getPassword());

        return new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis/mybatis-config.xml"), properties);
    }

    protected UserDto saveUserToDB() {
        UserDto userDto = createUserDto();
        dao.save(userDto);
        return userDto;
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .email("test@gmail.com")
                .password("testPassword")
                .link(UUID.randomUUID().toString())
                .active(false)
                .build();
    }
}
