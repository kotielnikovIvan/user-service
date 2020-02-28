package com.fitgoal.service.kafka.consumer;

import com.fitgoal.service.kafka.model.EventMessage;

import java.util.List;

public interface EventConsumer {
    List<EventMessage> getEvents();
}
