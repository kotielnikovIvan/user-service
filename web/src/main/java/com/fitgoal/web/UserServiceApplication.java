package com.fitgoal.web;

import com.fitgoal.api.LoginService;
import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.ResetPasswordService;
import com.fitgoal.dao.UserDao;
import com.fitgoal.dao.impl.UserDaoImpl;
import com.fitgoal.service.LoginServiceImpl;
import com.fitgoal.service.RegistrationServiceImpl;
import com.fitgoal.service.ResetPasswordServiceImpl;
import com.fitgoal.service.kafka.consumer.EventConsumer;
import com.fitgoal.service.kafka.consumer.UserEventConsumer;
import com.fitgoal.service.kafka.kStream.UserServiceKStream;
import com.fitgoal.service.kafka.producer.EventProducer;
import com.fitgoal.service.kafka.producer.UserEventProducer;
import com.fitgoal.web.config.UserServiceConfiguration;
import com.fitgoal.web.exceptionmapper.IncorrectEmailOrPasswordExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserAlreadyExistExceptionMapper;
import com.fitgoal.web.exceptionmapper.UserNotFoundExceptionMapper;
import com.fitgoal.web.resources.ConsumerResource;
import com.fitgoal.web.resources.LoginResource;
import com.fitgoal.web.resources.RegistrationResource;
import com.fitgoal.web.resources.ResetPasswordResource;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class UserServiceApplication extends Application<UserServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new UserServiceApplication().run(args);
    }

    public String getName() {
        return "user-service";
    }

    public void run(UserServiceConfiguration config, Environment environment) throws Exception {

        JerseyEnvironment jersey = environment.jersey();
        final SqlSessionManager sessionManager = buildSqlSessionManager(config);
        final Producer<String, String> producer = buildProducer(config);
        final Consumer<String, String> consumer = buildConsumer(config);
        UserEventProducer userEventProducer = new UserEventProducer(producer);
        UserEventConsumer userEventConsumer = new UserEventConsumer(consumer);
        UserServiceKStream kStream = new UserServiceKStream();
        jersey.register(new AbstractBinder() {
            protected void configure() {
                bind(LoginServiceImpl.class).to(LoginService.class).in(Singleton.class);
                bind(RegistrationServiceImpl.class).to(RegistrationService.class).in(Singleton.class);
                bind(ResetPasswordServiceImpl.class).to(ResetPasswordService.class).in(Singleton.class);
                bind(UserDaoImpl.class).to(UserDao.class).in(Singleton.class);
                bind(sessionManager).to(SqlSessionManager.class);
                bind(userEventProducer).to(EventProducer.class);
                bind(userEventConsumer).to(EventConsumer.class);
                bind(new UserServiceKStream()).to(UserServiceKStream.class);
//                bind(kStream).to(UserServiceKStream.class);
            }
        });
        jersey.register(ResetPasswordResource.class);
        jersey.register(LoginResource.class);
        jersey.register(RegistrationResource.class);
        jersey.register(ConsumerResource.class);

        jersey.register(UserNotFoundExceptionMapper.class);
        jersey.register(IncorrectEmailOrPasswordExceptionMapper.class);
        jersey.register(UserAlreadyExistExceptionMapper.class);

        jersey.register(UserServiceKStream.class);

    }

    private SqlSessionManager buildSqlSessionManager(UserServiceConfiguration config) {
        Properties properties = new Properties();
        properties.setProperty("url", config.getDataSourceFactory().getUrl());
        properties.setProperty("username", config.getDataSourceFactory().getUser());
        properties.setProperty("password", config.getDataSourceFactory().getPassword());

        try (Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml")) {
            return SqlSessionManager.newInstance(reader, properties);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Producer<String, String> buildProducer(UserServiceConfiguration config) {
        return new KafkaProducer<>(config.getProducer());
    }

    private Consumer<String, String> buildConsumer(UserServiceConfiguration config) {
        return new KafkaConsumer<>(config.getConsumer());
    }
}
