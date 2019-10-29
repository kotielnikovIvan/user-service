package com.fitgoal.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class UserNewPasswordData {

    @JsonProperty("password")
    @NotEmpty
    @Length(min = 8)
    private String newPassword;

}
