package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "label")
public class Label extends Domain {
    @Column(name = "name")
    private String name;
    @Column(name = "card_id")
    private UUID cardId;
   // private Color color;

}

