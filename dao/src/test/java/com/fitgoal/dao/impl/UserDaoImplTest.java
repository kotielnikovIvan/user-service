package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.util.MySqlTestHelper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoImplTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("test_db")
            .withInitScript("sqlScripts/create-db-schema.sql");

    private UserDao dao;
    private MySqlTestHelper testHelper = new MySqlTestHelper(mySQLContainer);

    @Before
    public void setUp() throws IOException {
        SqlSessionManager sessionManager = SqlSessionManager.newInstance(getSqlSessionFactory());
        dao = new UserDaoImpl(sessionManager);
    }

    @After
    public void cleanUp() {
        testHelper.deleteAll("users");
    }

    @Test
    public void findByEmail_whenUserExists_assertContentEqual() {
        UserDto testUser = saveUser();
        String email = testUser.getEmail();

        Optional<UserDto> actualUser = dao.findByEmail(email);

        assertThat(actualUser).contains(testUser);
    }

    @Test
    public void findByEmail_whenUserNotExists_assertContentEmpty() {
        String email = "wrongEmail@gmail.com";

        Optional<UserDto> testUser = dao.findByEmail(email);

        assertThat(testUser).isEmpty();
    }

    @Test
    public void findByLink_whenUserExists_assertContentEqual() {
        UserDto testUser = saveUser();
        String link = testUser.getLink();

        Optional<UserDto> actualUser = dao.findByLink(link);

        assertThat(actualUser).contains(testUser);
    }

    @Test
    public void findByLink_whenUserNotExists_assertContentEmpty() {
        String link = "wrongLink";

        Optional<UserDto> testUser = dao.findByLink(link);

        assertThat(testUser).isEmpty();
    }

    @Test
    public void updateUser_whenUserExists_assertContentEqual() {
        UserDto testUser = saveUser();
        testUser.setEmail("updated@gmail.com");
        testUser.setLink("updatedLink");

        UserDto actualUser = dao.update(testUser);

        assertThat(actualUser).isEqualTo(testUser);
    }

    private UserDto saveUser() {
        UserDto testUser = createUser();
        dao.save(testUser);
        return testUser;
    }

    private static UserDto createUser() {
        return UserDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("testPassword")
                .link("testLink")
                .active(false)
                .build();
    }

    private static SqlSessionFactory getSqlSessionFactory() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("url", mySQLContainer.getJdbcUrl());
        properties.setProperty("username", mySQLContainer.getUsername());
        properties.setProperty("password", mySQLContainer.getPassword());

        return new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis/mybatis-config.xml"), properties);
    }
}