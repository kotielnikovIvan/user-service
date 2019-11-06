package com.fitgoal.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private String id;

    private String email;

    private String password;

    private String link;

    private boolean active;

}
