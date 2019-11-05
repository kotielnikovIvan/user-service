package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class UserEmailData {

    @JsonProperty
    @Email
    private String email;

}
