package spd.trello.domain;

import lombok.*;
import spd.trello.domain.common.Resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table
public class BoardTemplate extends Resource {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}

