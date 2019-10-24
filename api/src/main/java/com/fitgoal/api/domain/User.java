package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Unique
    @NotEmpty
    @JsonProperty
    private Long id;

    @NotEmpty
    @JsonProperty
    private String email;

    @NotEmpty
    @JsonProperty
    private String password;

    @JsonProperty
    private String link;

    @JsonProperty
    @NotEmpty
    private boolean active = false;
}
