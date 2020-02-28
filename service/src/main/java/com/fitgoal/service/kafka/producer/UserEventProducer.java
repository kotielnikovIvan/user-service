package com.fitgoal.service.kafka.producer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class UserEventProducer implements EventProducer{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventProducer.class);

    private static final String TOPIC = "user-service-topic";

    private final Producer<String, String> producer;

    @Inject
    public UserEventProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public void sendMessage(String key, String message) {
        producer.send(new ProducerRecord<>(
                TOPIC,
                key,
                message), (record, ex) -> {
                    if(ex != null) {
                        System.out.println("Some error occurred while sending message to kafka " + TOPIC + " topic.");
                    } else {
                        System.out.println("Message was sent to topic: " + record.topic() + ", partition: " + record.partition());
                    }
                }
        );
    }
}
