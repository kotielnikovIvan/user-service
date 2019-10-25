package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class UserNewPasswordData {

    @JsonProperty
    @NotEmpty
    private String newPassword;

}
