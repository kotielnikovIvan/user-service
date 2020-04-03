package com.fitgoal.web.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.service.kafka.consumer.EventConsumer;
import com.fitgoal.service.kafka.kStream.UserServiceKStream;
import com.fitgoal.service.kafka.model.EventMessage;
import org.eclipse.jetty.http.HttpStatus;


@Path("/consumer")
@Produces(MediaType.APPLICATION_JSON)
public class ConsumerResource {

//    private UserServiceKStream kStream;
    private EventConsumer consumer;

    @Inject
    public ConsumerResource(EventConsumer consumer, UserServiceKStream kStream) {
        this.consumer = consumer;
//        this.kStream = kStream;
    }

    @GET
    @Timed
    public List<EventMessage> getMessages() {
        return consumer.getEvents();
    }
//
//    @GET
//    @Timed
//    @Path("/stream")
//    public void getStreamMessages() {
//
//    }
}
