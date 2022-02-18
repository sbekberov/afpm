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
    @Column(name = "cardTeamplate_id")
    private UUID CardTeamplateId;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Reminder reminder;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "card_member",
            joinColumns=@JoinColumn(name= "card_id")
    )
    @Column(name = "member_id")
    private Set<UUID> membersIds = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("card")
    private List<CheckList> checkLists = new ArrayList<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "card"
    )
    @Column(name = "attachment_id")
    private Set<UUID> attachmentIds = new HashSet<>();



}

