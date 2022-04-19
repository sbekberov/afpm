package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import spd.trello.domain.common.Domain;
import spd.trello.domain.common.Resource;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class Color extends Domain {
    @Column(name = "red")
    @Size(min = 0, max = 255, message = "The red color should be in the range 0 to 255.")
    private Integer red = 0;
    @Column(name = "green")
    @Size(min = 0, max = 255, message = "The green color should be in the range 0 to 255.")
    private Integer green = 0;
    @Column(name = "blue")
    @Size(min = 0, max = 255, message = "The blue color should be in the range 0 to 255.")
    private Integer blue = 0;
    @OneToOne (mappedBy = "color", cascade = CascadeType.ALL)
    @JsonIgnore
    private Label labelId;
}
