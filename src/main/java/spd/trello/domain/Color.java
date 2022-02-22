package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Domain;
import spd.trello.domain.common.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class Color extends Domain {
    @Column(name = "red")
    private Integer red = 0;
    @Column(name = "green")
    private Integer green = 0;
    @Column(name = "blue")
    private Integer blue = 0;
    @Column(name = "label_id")
    private UUID labelId;
}
