package com.fitgoal.dao.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String link = UUID.randomUUID().toString();

    private boolean active;

    public UserDto(String email, String password, boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }
}
