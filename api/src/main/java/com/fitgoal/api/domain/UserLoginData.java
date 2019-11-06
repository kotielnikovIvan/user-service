package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginData {

    @JsonProperty
    @Email
    @NotEmpty
    private String email;

    @JsonProperty
    @NotEmpty
    private String password;

}
