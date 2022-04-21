package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table (name = "checklist")
public class CheckList extends Resource {
    @Column(name = "name")
    private String name;
    @Column(name = "card_id")
    private UUID cardId;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckableItem> items = new ArrayList<>();
}
