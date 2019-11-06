package com.fitgoal.dao.impl;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.util.MySqlTestHelper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.*;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoImplTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withDatabaseName("test_db")
            .withInitScript("sqlScripts/create-db-schema.sql");

    private static UserDao dao;
    private static MySqlTestHelper testHelper;

    @BeforeClass
    public static void setUpSessionManager() {
        SqlSessionManager sessionManager = SqlSessionManager.newInstance(getSqlSessionFactory());
        dao = new UserDaoImpl(sessionManager);
        testHelper = new MySqlTestHelper(mySQLContainer);
    }

    @After
    public void deleteAllFromDBAfterEachTest(){
        testHelper.deleteAll("users");
    }

    @Test
    public void testCreateUserAndGetByEmail() {
        UserDto testUser = createUser();
        String email = testUser.getEmail();
        dao.save(testUser);

        Optional<UserDto> actualUser = dao.findByEmail(email);

        assertThat(actualUser).contains(testUser);
    }

    @Test
    public void testGetUserByLink() {
        UserDto testUser = createUser();
        String link = testUser.getLink();
        dao.save(testUser);

        Optional<UserDto> actualUser = dao.findByLink(link);

        assertThat(actualUser).contains(testUser);
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

    private static SqlSessionFactory getSqlSessionFactory() {
        Properties properties = new Properties();

        properties.setProperty("url", mySQLContainer.getJdbcUrl());
        properties.setProperty("username", mySQLContainer.getUsername());
        properties.setProperty("password", mySQLContainer.getPassword());

        try (InputStream is = Resources.getResourceAsStream("mybatis/mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(is, properties);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}