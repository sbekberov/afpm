package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Board extends Resource {
    private String name;
    private String description;
    private List<CardList> cardLists;
    private List<Member> members;
 //   private boolean favouriteStatus;  //TODO
    private Boolean archived = Boolean.FALSE;
    private UUID workspaceId;
    private BoardVisibility visibility = BoardVisibility.PRIVATE;

}
