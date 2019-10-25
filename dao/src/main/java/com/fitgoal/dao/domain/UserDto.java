package com.fitgoal.dao.domain;

import lombok.*;
import org.graalvm.compiler.hotspot.sparc.SPARCHotSpotSafepointOp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
/*@NamedQueries({@NamedQuery(name = "com.fitgoal.dao.domain.UserDto",
        query = "select u from u UserDto where u.email = email")
})*/
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String link;

    @NotNull
    private boolean active = false;

    public UserDto(@Email @NotNull String email, @NotNull String password, @NotNull boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }

    @PrePersist
    private void prePersist() {
        link = UUID.randomUUID().toString();
    }
}
