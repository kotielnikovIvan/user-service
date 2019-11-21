package com.fitgoal.integration;

import org.junit.AfterClass;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MySQLContainer;

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
    protected IntegrationTestHelper testHelper = new IntegrationTestHelper(mySQLContainer);

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

    @After
    public void cleanUp() {
        testHelper.deleteAll("users");
    }
}
