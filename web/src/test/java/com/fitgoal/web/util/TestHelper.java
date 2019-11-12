package com.fitgoal.web.util;

import io.dropwizard.db.DataSourceFactory;

public class TestHelper {

    public static DataSourceFactory createDataSourceFactory() {
        DataSourceFactory sourceFactory = new DataSourceFactory();
        sourceFactory.setDriverClass("com.mysql.jdbc.Driver");
        sourceFactory.setUrl("jdbc:mysql://localhost:3306/test_db");
        sourceFactory.setUser("test");
        sourceFactory.setPassword("test");
        return sourceFactory;
    }
}
