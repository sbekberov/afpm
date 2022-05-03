package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table (name = "checklist")
public class CheckList extends Resource {
    @Column(name = "name")
    @NotNull(message = "The name field must be filled.")
    @Size(min = 2, max = 30, message = "The name field must be between 2 and 30 characters long.")
    private String name;
    @Column(name = "card_id")
    private UUID cardId;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckableItem> items = new ArrayList<>();
}
