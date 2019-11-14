package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserLoginData {

    @JsonProperty
    @Email
    @NotEmpty
    private String email;

    @JsonProperty
    @NotEmpty
    private String password;

}
