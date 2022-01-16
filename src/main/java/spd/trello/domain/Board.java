package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Board extends Resource {
    private String name;
    private String description;
    private Boolean archived = Boolean.FALSE;
    private UUID workspaceId;
    private BoardVisibility visibility = BoardVisibility.PRIVATE;

}
