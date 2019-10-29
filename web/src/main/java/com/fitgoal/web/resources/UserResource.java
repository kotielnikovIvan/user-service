package com.fitgoal.web.resources;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.api.LoginService;
import com.fitgoal.api.RegistrationService;
import com.fitgoal.api.UserService;
import com.fitgoal.api.domain.User;
import com.fitgoal.api.domain.UserAccessData;
import com.fitgoal.api.domain.UserNewPasswordData;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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

    @Path("/login")
    @GET
    @Timed
    public User login(@NotNull @Valid UserAccessData user) {
        return loginService.login(user);
    }

    @Path("/register")
    @PUT
    @Timed
    public Response register(@NotNull @Valid UserAccessData user) {
        registrationService.register(user);
        return Response.noContent().build();
    }

    @Path("/register/verify/{link:[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}}")
    @POST
    @Timed
    public User activateUser(@PathParam("link") String verificationLink) {
        return registrationService.activateUser(verificationLink);
    }

    @Path("/forgotPassword")
    @GET
    @Timed
    public Response getEmailForNotification(@NotNull @Email @QueryParam("email") String email) {
        userService.notifyUser(email);
        return Response.noContent().build();
    }

    @Path("/forgotPassword/{link:[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}}/resetPassword")
    @POST
    @Timed
    public Response resetPassword(@PathParam("link") String link, UserNewPasswordData passwordData) {
        userService.resetPassword(link, passwordData.getNewPassword());
        return Response.noContent().build();
    }
}