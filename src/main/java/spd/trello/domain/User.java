package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;
import spd.trello.domain.enums.Role;
import spd.trello.domain.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Resource {
    @Column(name = "first_name")
    @NotNull(message = "The firstname field must be filled.")
    @Size(min = 2, max = 30, message = "The name field must be between 2 and 30 characters long.")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "The lastname field must be filled.")
    @Size(min = 2, max = 30, message = "The name field must be between 2 and 30 characters long.")

    private String lastName;
    @Column(name = "email")
    @NotNull(message = "The email field must be filled.")
    @Email(message = "The email field should look like email.")
    private String email;

    @NotEmpty
    @Column(name = "password")
    String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    Role role;
}
