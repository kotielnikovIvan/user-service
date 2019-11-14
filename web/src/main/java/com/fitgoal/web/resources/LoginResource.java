package com.fitgoal.web.resources;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.api.LoginService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserLoginData;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private LoginService loginService;

    @Inject
    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Timed
    public User login(@NotNull @Valid UserLoginData user) {
        return loginService.login(user);
    }
}
