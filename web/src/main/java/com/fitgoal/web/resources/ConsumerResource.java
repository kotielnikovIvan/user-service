package com.fitgoal.web.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.fitgoal.service.kafka.consumer.EventConsumer;
import com.fitgoal.service.kafka.model.EventMessage;


@Path("/consumer")
@Produces(MediaType.APPLICATION_JSON)
public class ConsumerResource {

    private EventConsumer consumer;

    @Inject
    public ConsumerResource(EventConsumer consumer) {
        this.consumer = consumer;
    }

    @GET
    @Timed
    public List<EventMessage> getMessages() {
        return consumer.getEvents();
    }

}
