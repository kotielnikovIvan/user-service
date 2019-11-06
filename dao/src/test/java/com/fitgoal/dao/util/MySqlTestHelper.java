package com.fitgoal.dao.util;

import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlTestHelper {

    private final MySQLContainer sqlContainer;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public MySqlTestHelper(MySQLContainer sqlContainer) {
        this.sqlContainer = sqlContainer;
        dbUrl = sqlContainer.getJdbcUrl();
        dbUsername = sqlContainer.getUsername();
        dbPassword = sqlContainer.getPassword();
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
}