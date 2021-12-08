package com.trello.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Card {
    private String name;
    private String description;
    private List<Member> assignedMembers;
    private List<Label> labels;
    private boolean isArchived;
    private List<Comment> comments;
    private Reminder reminder;
    private List<Checklist> checklists;
    private LocalDateTime creationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Member> getAssignedMembers() {
        return assignedMembers;
    }

    public void setAssignedMembers(List<Member> assignedMembers) {
        this.assignedMembers = assignedMembers;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}

