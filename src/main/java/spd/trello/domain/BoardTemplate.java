package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table
public class BoardTemplate extends Resource {
    @Column(name = "name")
    @Size(min = 2, max = 30, message = "The name field must be between 2 and 30 characters long.")
    private String name;
    @Column(name = "description")
    @Size(min = 2, max = 255, message = "The description field must be between 2 and 255 characters long.")
    private String description;
}

