package com.fitgoal.integration.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.testcontainers.containers.MySQLContainer;

import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.impl.UserDaoImpl;

public class IntegrationTestHelper {

    private final MySQLContainer sqlContainer;
    private final UserDao dao;
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    public IntegrationTestHelper(MySQLContainer sqlContainer) {
        this.sqlContainer = sqlContainer;
        dbUsername = sqlContainer.getUsername();
        dbPassword = sqlContainer.getPassword();
        dbUrl = sqlContainer.getJdbcUrl();
        dao = new UserDaoImpl(SqlSessionManager.newInstance(getSqlSessionFactory()));
    }

    public void deleteAll(String tableName) {
        try {
            Class.forName(sqlContainer.getDriverClassName());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {
            String query = "DELETE FROM " + tableName;
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public UserDto saveUser() {
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

    private static SqlSessionFactory getSqlSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("url", dbUrl);
        properties.setProperty("username", dbUsername);
        properties.setProperty("password", dbPassword);
        try {
            return new SqlSessionFactoryBuilder()
                    .build(Resources.getResourceAsStream("mybatis/mybatis-config.xml"), properties);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
