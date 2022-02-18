package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "card_list")
public class CardList extends Resource {
    @Column(name = "name")
    private String name;
    @Column(name = "archived")
    private Boolean archived = Boolean.FALSE;
    @Column(name = "board_id")
    private UUID BoardId;
    @Column(name = "description")
    private String description;
}

