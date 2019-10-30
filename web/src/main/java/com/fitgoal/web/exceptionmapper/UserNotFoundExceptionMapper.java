package com.fitgoal.web.exceptionmapper;

import com.fitgoal.api.exceptions.UserNotFoundException;

import javax.ws.rs.core.Response;

public class UserNotFoundExceptionMapper extends AbstractExceptionMapper<UserNotFoundException> {

    @Override
    protected Response.Status getStatus(Exception exception) {
        return Response.Status.NOT_FOUND;
    }
}
