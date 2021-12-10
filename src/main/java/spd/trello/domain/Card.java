package spd.trello.domain;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Card {
    private String name;
    private String description;
    private List<Member> assignedMembers;
    private List<Label> labels;
    private Boolean archived = Boolean.FALSE;
    private List<Comment> comments;
    private Reminder reminder;
    private List<Checklist> checklists;
    private LocalDateTime creationDate;

}

