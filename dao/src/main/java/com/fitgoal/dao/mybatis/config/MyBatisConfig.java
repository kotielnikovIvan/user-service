package com.fitgoal.dao.mybatis.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisConfig {

    private static SqlSessionFactory sessionFactory;

    private MyBatisConfig() {

    }

    static {
        try (Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
