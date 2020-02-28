package com.fitgoal.service.kafka.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventMessage {

    private String key;
    private String value;
    private String topic;
    private Long offset;
    private int partition;

}
