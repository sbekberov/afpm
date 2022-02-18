package spd.trello.domain;


import lombok.*;
import spd.trello.domain.common.Domain;
import spd.trello.domain.common.Resource;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table
public class CheckList extends Resource {
    @Column(name = "name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckableItem> items = new ArrayList<>();
}
