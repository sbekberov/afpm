package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import spd.trello.domain.common.Domain;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table
public class CheckableItem extends Domain {
    @Column(name = "name")
    private String name;
    @Column(name = "checked")
    private Boolean checked = Boolean.FALSE;
    @Column(name = "checklist_id")
    private UUID checklistId;;
}

