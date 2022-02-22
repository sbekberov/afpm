package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Domain;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table
public class CheckableItem extends Domain {
    @Column(name = "name")
    private String name;
    @Column(name = "checked")
    private Boolean checked = Boolean.FALSE;
    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;
}

