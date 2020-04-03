package com.fitgoal.service.kafka.kStream;

import com.fitgoal.service.kafka.model.EventMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserServiceKStream {

    private final static Logger log = LoggerFactory.getLogger(UserServiceKStream.class);

    private static final String TOPIC = "user-service-topic";

    private static List<EventMessage> events = new ArrayList<>();

    private KafkaStreams kafkaStreams;

    public UserServiceKStream() {
        this.kafkaStreams = createKafkaStreams();
    }

//    public static void main(String[] args) {
////        createKafkaStreams();
////    }

    public static KafkaStreams createKafkaStreams() {

        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-service");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> stream = streamsBuilder.stream(TOPIC /*Consumed.with(Serdes.String(), Serdes.String())*/);
        stream.foreach((k, v) -> {
            events.add(buildEventMessage(k, v));
            logMessage(k, v);
        });

        return new KafkaStreams(streamsBuilder.build(), properties);
    }

    private static void logMessage(String key, String value) {
        log.info("Received value to process. key={}, value={}", key, value);
    }

    private static EventMessage buildEventMessage(String key, String value) {
        return EventMessage.builder()
                .key(key)
                .value(value)
                .build();
    }

    @PostConstruct
    public void start() throws Exception {
        kafkaStreams.start();
    }

//    @Override
//    public void stop() throws Exception {
//        kafkaStreams.close();
//    }
}