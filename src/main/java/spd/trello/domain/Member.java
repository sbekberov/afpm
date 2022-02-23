package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;
import spd.trello.domain.enums.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member extends Resource {
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;
    @Column(name = "user_id")
    private UUID userId;
}
