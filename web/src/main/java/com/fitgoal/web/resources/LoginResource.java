package com.fitgoal.web.resources;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.api.Login;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private Login login;

    @Inject
    public LoginResource(Login login) {
        this.login = login;
    }

    @GET
    @Timed
    public User login(@NotNull @Valid UserLoginData user) {
        return login.login(user);
    }
}
