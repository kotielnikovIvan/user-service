package com.fitgoal.service.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserEvent {

    private String email;
    private String password;
}
