package com.fitgoal.web.resources;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.api.ResetPassword;
import com.fitgoal.api.domain.UserEmailData;
import com.fitgoal.api.domain.UserNewPasswordData;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/forgotPassword")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResetPasswordResource {

    private final ResetPassword resetPassword;

    @Inject
    public ResetPasswordResource(ResetPassword resetPassword) {
        this.resetPassword = resetPassword;
    }

    @GET
    @Timed
    public void sendEmailForResetPassword(UserEmailData email) {
        resetPassword.sendEmailForResetPassword(email);
    }

    @Path("/{link:[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}}/resetPassword")
    @POST
    @Timed
    public void resetPassword(@PathParam("link") String link, UserNewPasswordData passwordData) {
        resetPassword.resetPassword(link, passwordData.getNewPassword());
    }
}