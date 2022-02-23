package spd.trello.domain;


import lombok.Getter;
import lombok.Setter;
import spd.trello.domain.common.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "attachment")
public class Attachment extends Resource {
    @Column(name = "link")
    private String link;
    @Column(name = "name")
    private String name;
    @Column(name = "card_id")
    private UUID cardId;
}
