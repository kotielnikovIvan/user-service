package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

@Getter
@Setter
@NoArgsConstructor
public class User {

    @Unique
    @JsonProperty
    private Long id;

    @NotEmpty
    @JsonProperty
    @Email
    private String email;

    @JsonProperty
    private String password;

    @JsonProperty
    private String link;

    @JsonProperty
    private boolean active = false;

    public User(@Unique Long id, @NotEmpty @Email String email, boolean active) {
        this.id = id;
        this.email = email;
        this.active = active;
    }
}
