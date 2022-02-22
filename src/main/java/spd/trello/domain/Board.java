package spd.trello.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domain.common.Resource;
import spd.trello.domain.enums.BoardVisibility;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class Board extends Resource {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "archived")
    private Boolean archived = Boolean.FALSE;
    @Column(name = "workspace_id")
    private UUID workspaceId;
    @Column(name = "visibility")
    @Enumerated(EnumType.STRING)
    private BoardVisibility visibility = BoardVisibility.PRIVATE;
    @Column(name = "boardTeamplate_id")
    private UUID BoardTeamplateId;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "board_member",
            joinColumns=@JoinColumn(name= "board_id")
    )
    @Column(name = "member_id")
    private Set<UUID> membersIds = new HashSet<>();

}
