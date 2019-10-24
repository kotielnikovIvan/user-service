package com.fitgoal.web.resources;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.api.LoginService;
import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.UserService;
import com.fitgoal.api.domain.User;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/users")
@Produces({"application/json"})
public class UserResource {
    private final LoginService loginService;
    private final RegistrationService registrationService;
    private final UserService userService;

    @Inject
    public UserResource(LoginService loginService, RegistrationService registrationService, UserService userService) {
        this.loginService = loginService;
        this.registrationService = registrationService;
        this.userService = userService;
    }

    @Path("/register")
    @POST
    @Timed
    public User register() {
        return new User();
    }

    @Path("/{id}/login")
    @GET
    @Timed
    public User login(@PathParam("id") Long userId) {
        return loginService.login(userId);
    }

    @Path("/{id}/resetPassword")
    @PUT
    @Timed
    public User resetPassword(@PathParam("id") Long userId) {
        return new User();
    }
}