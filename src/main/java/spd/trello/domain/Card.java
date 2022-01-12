package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Card extends Resource{
    private String name;
    private String description;
    private List<Member> assignedMembers;
    private List<Label> labels;
    private Boolean archived = Boolean.FALSE;
    private List<Comment> comments;
    private Reminder reminder;
    private List<Checklist> checklists;
    private UUID cardListId;

}

