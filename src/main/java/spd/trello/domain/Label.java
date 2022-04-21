package spd.trello.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domain.common.Domain;
import spd.trello.domain.common.Resource;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "label")
public class Label extends Resource {
    @Column(name = "name")
    private String name;
    @Column(name = "card_id")
    private UUID card_id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private Color color;





}

