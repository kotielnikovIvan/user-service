package com.fitgoal.web.exceptionmapper;

import io.dropwizard.jersey.errors.ErrorMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class AbstractExceptionMapper<E extends Exception> implements ExceptionMapper<E> {
    @Override
    public Response toResponse(E exception) {

        Response.Status status = getStatus(exception);
        return Response
                .status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorMessage(status.getStatusCode(), exception.getMessage()))
                .build();
    }

    protected abstract Response.Status getStatus(Exception exception);

}
