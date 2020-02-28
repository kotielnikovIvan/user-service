package com.fitgoal.service.kafka.kStream;

import io.dropwizard.lifecycle.Managed;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class UserServiceKStream {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceKStream.class);

    private static final String TOPIC = "user-service-topic";

    private KafkaStreams kafkaStreams;

    public UserServiceKStream() {
        kafkaStreams = createKStream();
    }

    public KafkaStreams createKStream() {

        Properties properties = new Properties();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-service-application");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> stream = streamsBuilder.stream(TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        stream.foreach(UserServiceKStream::logMessage);

        return new KafkaStreams(streamsBuilder.build(), properties);
    }

    private static void logMessage(String key, String value) {
        LOGGER.info("Received value to process. key={}, value={}", key, value);
    }

    public void start() {
        kafkaStreams.start();
    }

    public void stop() {
        kafkaStreams.close();
    }

//    public static void main(String[] args) {
//        Properties properties = new Properties();
//
//        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-service-application");
//        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//
//        StreamsBuilder streamsBuilder = new StreamsBuilder();
//
//        KStream<String, String> stream = streamsBuilder.stream(TOPIC);
//
//        stream.foreach((k, v) -> {
//            System.out.println("message, " + k + "  " + v);
//        });
//
//        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
//        System.out.println("kafka started");
//        kafkaStreams.start();
//        System.out.println("-----------");
//        kafkaStreams.close();
//        System.out.println("kafka stopped");
//      }
}