package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CheckableItem extends Domain {
    private String name;
    private Boolean checked = Boolean.FALSE;
    private UUID checklistId;
}

