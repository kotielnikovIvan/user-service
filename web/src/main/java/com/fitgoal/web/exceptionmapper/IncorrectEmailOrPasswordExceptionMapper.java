package com.fitgoal.web.exceptionmapper;

import com.fitgoal.api.exceptions.IncorrectEmailOrPasswordException;

import javax.ws.rs.core.Response;

public class IncorrectEmailOrPasswordExceptionMapper extends AbstractExceptionMapper<IncorrectEmailOrPasswordException> {
    @Override
    protected Response.Status getStatus(Exception exception) {
        return Response.Status.BAD_REQUEST;
    }
}
