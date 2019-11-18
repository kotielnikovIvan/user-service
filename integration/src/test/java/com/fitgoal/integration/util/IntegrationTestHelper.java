package com.fitgoal.integration.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testcontainers.containers.MySQLContainer;

public class IntegrationTestHelper {

    private final MySQLContainer sqlContainer;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public IntegrationTestHelper(MySQLContainer sqlContainer) {
        this.sqlContainer = sqlContainer;
        dbUsername = sqlContainer.getUsername();
        dbPassword = sqlContainer.getPassword();
        dbUrl = sqlContainer.getJdbcUrl();
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
