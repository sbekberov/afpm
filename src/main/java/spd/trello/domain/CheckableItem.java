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
@Table(name = "checkable_item")
public class CheckableItem extends Domain {
    @Column(name = "name")
    private String name;
    @Column(name = "checked")
    private Boolean checked = Boolean.FALSE;
}

