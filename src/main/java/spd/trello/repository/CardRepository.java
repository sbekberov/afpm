package spd.trello.repository;

import spd.trello.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class CardRepository {
    public void add (Card card){
        UUID id = card.getId();
        UUID cardListId = card.ge
        String updatedBy = card.getUpdatedBy();
        String createdBy = card.getCreatedBy();
        LocalDateTime createdDate = card.getCreatedDate();
        LocalDateTime updatedDate = card.getUpdatedDate();
        String name = card.getName();
        String description = card.getDescription();
        Boolean archived = card.getArchived();


    }
}

    private List<Member> assignedMembers;
    private List<Label> labels;
    private List<Comment> comments;
    private Reminder reminder;
    private List<Checklist> checklists;
    private LocalDateTime creationDate;