package com.fitgoal.service.kafka.producer;

public interface EventProducer {
    void sendMessage(String key, String message);
}
