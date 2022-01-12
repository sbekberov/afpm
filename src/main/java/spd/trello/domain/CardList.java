package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardList extends Resource{
    private String name;
    private List<Card> cards;
    private Boolean archived = Boolean.FALSE;
    private UUID BoardId;

}

