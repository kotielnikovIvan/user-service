package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @JsonProperty
    @NotNull
    private Long id;

    @JsonProperty
    @Email
    @NotEmpty
    private String email;

    @JsonProperty
    @NotNull
    private String link;

    @JsonProperty
    @NotNull
    private boolean active;

}
