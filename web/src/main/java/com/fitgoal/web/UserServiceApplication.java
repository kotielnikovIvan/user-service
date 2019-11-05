package com.fitgoal.web;

import com.fitgoal.api.Login;
import com.fitgoal.api.Registration;
import com.fitgoal.api.ResetPassword;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.impl.UserDaoImpl;
import com.fitgoal.service.LoginService;
import com.fitgoal.service.RegistrationService;
import com.fitgoal.service.ResetPasswordService;
import com.fitgoal.web.config.UserServiceConfiguration;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserAlreadyExistExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserNotFoundExceptionMapper;
import com.fitgoal.web.resources.LoginResource;
import com.fitgoal.web.resources.RegistrationResource;
import com.fitgoal.web.resources.ResetPasswordResource;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.Reader;

public class UserServiceApplication extends Application<UserServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new UserServiceApplication().run(args);
    }

    public String getName() {
        return "user-service";
    }

    public void initialize(Bootstrap<UserServiceConfiguration> bootstrap) {

    }

    public void run(UserServiceConfiguration config, Environment environment) throws Exception {

        JerseyEnvironment jersey = environment.jersey();

        final SqlSessionManager sessionManager = buildSqlSessionManager();

        jersey.register(new AbstractBinder() {
            protected void configure() {
                bind(LoginService.class).to(Login.class).in(Singleton.class);
                bind(RegistrationService.class).to(Registration.class).in(Singleton.class);
                bind(ResetPasswordService.class).to(ResetPassword.class).in(Singleton.class);
                bind(UserDaoImpl.class).to(UserDao.class).in(Singleton.class);

                bind(sessionManager).to(SqlSessionManager.class);
            }
        });
        jersey.register(ResetPasswordResource.class);
        jersey.register(LoginResource.class);
        jersey.register(RegistrationResource.class);

        jersey.register(UserNotFoundExceptionMapper.class);
        jersey.register(IncorrectEmailOrPasswordExceptionMapper.class);
        jersey.register(UserAlreadyExistExceptionMapper.class);
    }

    private SqlSessionManager buildSqlSessionManager() {
        try(Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml")) {
            return SqlSessionManager.newInstance(reader);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
