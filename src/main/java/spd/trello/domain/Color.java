package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import spd.trello.domain.common.Domain;
import spd.trello.domain.common.Resource;

import javax.persistence.*;
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
    @OneToOne (mappedBy = "color", cascade = CascadeType.ALL)
    @JsonIgnore
    private Label labelId;
}
