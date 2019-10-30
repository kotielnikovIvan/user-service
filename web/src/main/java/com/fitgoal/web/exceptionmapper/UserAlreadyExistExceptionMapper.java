package com.fitgoal.web.exceptionmapper;

import com.fitgoal.api.exceptions.UserAlreadyExistException;

import javax.ws.rs.core.Response;

public class UserAlreadyExistExceptionMapper extends AbstractExceptionMapper<UserAlreadyExistException> {
    @Override
    protected Response.Status getStatus(Exception exception) {
        return Response.Status.BAD_REQUEST;
    }
}
