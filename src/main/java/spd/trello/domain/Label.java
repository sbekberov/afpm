package spd.trello.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domain.common.Domain;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "label")
public class Label extends Domain {
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToMany
    @JoinTable(name = "card_label",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    private List<Card> cards = new ArrayList<>();



}

