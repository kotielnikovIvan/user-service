package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class UserRegistrationData {

    @JsonProperty
    @Email
    private String email;

    @JsonProperty
    @NotEmpty
    @Length(min = 8, max = 12)
    private String password;

}
