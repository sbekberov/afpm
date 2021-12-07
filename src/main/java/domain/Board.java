package domain;

import java.util.List;

public class Board {
    private String name;
    private String description;
    private List<CardList> cardListList;
    private List<Member> members;
    private BoardVisibilityEnum visibilityEnum;
    private boolean favouriteStatus;
    private boolean isArchived;
}
