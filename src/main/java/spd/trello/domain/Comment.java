package spd.trello.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domain.common.Resource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends Resource {
    @Column(name = "text")
    private String text;
    @Column(name = "card_id")
    private UUID cardId;
    @Column(name = "member_id")
    private UUID memberId;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "comments"
    )
    @Column(name = "attachment_id")
    private Set<UUID> attachmentIds = new HashSet<>();
}
