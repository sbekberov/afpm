package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardList extends Resource{
    private String name;
    private Boolean archived = Boolean.FALSE;
    private UUID BoardId;

}

