package com.fitgoal.service.kafka.consumer;

import com.fitgoal.service.kafka.model.EventMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserEventConsumer implements EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserEventConsumer.class);

    private Consumer<String, String> consumer;

    private final static String TOPIC = "user-service-topic";

    private List<EventMessage> messages = new ArrayList<>();

    public UserEventConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public List<EventMessage> getEvents() {
        consumer.subscribe(Collections.singleton(TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

        records.forEach(record -> {
            messages.add(buildEventMessage(record));
            log.info("Received value to process, key={}, value={}", record.key(), record.value());
        });
        consumer.commitAsync();
        return messages;
    }

    private EventMessage buildEventMessage(ConsumerRecord<String, String> record) {
        return EventMessage.builder()
                .key(record.key())
                .offset(record.offset())
                .value(record.value())
                .topic(record.topic())
                .partition(record.partition())
                .build();
    }
}
