package com.fitgoal.web.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Properties;

@Getter
@Setter
public class UserServiceConfiguration extends Configuration {
    @NotNull
    @Valid
    @JsonProperty("database")
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @NotNull
    private Properties producer;

    @NotNull
    private Properties consumer;

//    @NotNull
//    private Properties userServiceKStream;
}

