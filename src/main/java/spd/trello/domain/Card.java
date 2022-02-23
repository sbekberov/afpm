package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domain.common.Resource;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "card")
public class Card extends Resource {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "archived")
    private Boolean archived = Boolean.FALSE;
    @Column(name = "card_list_id")
    private UUID cardListId;
    @Column(name = "cardTemplate_id")
    private UUID cardTemplateId;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reminder_id")
    private Reminder reminder;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "attachment",
            joinColumns=@JoinColumn(name= "card_id")
    )
    @Column(name = "id")
    private Set<UUID> attachmentIds = new HashSet<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "checklist",
            joinColumns=@JoinColumn(name= "card_id")
    )
    @Column(name = "id")
    private Set<UUID> checklists = new HashSet<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "label",
            joinColumns=@JoinColumn(name= "card_id")
    )
    @Column(name = "id")
    private Set<UUID> labels = new HashSet<>();

    //    @ManyToMany
//    @JoinTable(name = "card_label",
//            joinColumns = @JoinColumn(name = "card_id"),
//            inverseJoinColumns = @JoinColumn(name = "label_id"))
//    private List<Label> labels = new ArrayList<>();
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "comment",
            joinColumns=@JoinColumn(name= "card_id")
    )
    @Column(name = "id")
    private Set<UUID> comments = new HashSet<>();
}

