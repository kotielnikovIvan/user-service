package com.fitgoal.web;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.UserService;
import com.fitgoal.web.config.UserServiceConfiguration;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.domain.UserDto;
import com.fitgoal.dao.impl.UserDaoImpl;
import com.fitgoal.web.resources.UserResource;
import com.fitgoal.service.LoginServiceImpl;
import com.fitgoal.service.RegistrationServiceImpl;
import com.fitgoal.service.UserServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.hibernate.SessionFactory;

import javax.inject.Singleton;

public class UserServiceApplication extends Application<UserServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new UserServiceApplication().run(args);
    }

    private final HibernateBundle<UserServiceConfiguration> hibernateBundle = new HibernateBundle<UserServiceConfiguration>(UserDto.class) {

        public DataSourceFactory getDataSourceFactory(UserServiceConfiguration userServiceConfiguration) {
            return userServiceConfiguration.getDataSourceFactory();
        }
    };

    public String getName() {
        return "user-service";
    }

    public void initialize(Bootstrap<UserServiceConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    public void run(UserServiceConfiguration config, Environment environment) throws Exception {
        final UserDaoImpl userDao = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(UserDaoImpl.class, SessionFactory.class, hibernateBundle.getSessionFactory());

        environment.jersey().register(new AbstractBinder() {
            protected void configure() {
                bind(LoginServiceImpl.class).to(LoginService.class).in(Singleton.class);
                bind(RegistrationServiceImpl.class).to(RegistrationService.class).in(Singleton.class);
                bind(UserServiceImpl.class).to(UserService.class).in(Singleton.class);
                bind(userDao).to(UserDao.class).in(Singleton.class);
            }
        });

        environment.jersey().register(UserResource.class);
    }
}
