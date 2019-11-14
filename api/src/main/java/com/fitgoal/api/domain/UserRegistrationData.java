package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRegistrationData {

    @JsonProperty
    @Email
    @NotEmpty
    private String email;

    @JsonProperty
    @NotEmpty
    @Length(min = 8, max = 12)
    private String password;

}
