package com.fitgoal.web;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.UserService;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.impl.UserDaoImpl;
import com.fitgoal.service.LoginServiceImpl;
import com.fitgoal.service.RegistrationServiceImpl;
import com.fitgoal.service.UserServiceImpl;
import com.fitgoal.service.util.SimpleConverter;
import com.fitgoal.service.util.UserConverter;
import com.fitgoal.web.config.UserServiceConfiguration;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserAlreadyExistExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserNotFoundExceptionMapper;
import com.fitgoal.web.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

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

        jersey.register(new AbstractBinder() {
            protected void configure() {
                bind(LoginServiceImpl.class).to(LoginService.class).in(Singleton.class);
                bind(RegistrationServiceImpl.class).to(RegistrationService.class).in(Singleton.class);
                bind(UserServiceImpl.class).to(UserService.class).in(Singleton.class);
                bind(UserDaoImpl.class).to(UserDao.class).in(Singleton.class);
                bind(UserConverter.class).to(SimpleConverter.class);
            }
        });
        jersey.register(UserResource.class);
        jersey.register(UserNotFoundExceptionMapper.class);
        jersey.register(IncorrectEmailOrPasswordExceptionMapper.class);
        jersey.register(UserAlreadyExistExceptionMapper.class);
    }
}
