package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;
import spd.trello.domain.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Resource {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @NotEmpty
    @Column(name = "password")
    String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

}
