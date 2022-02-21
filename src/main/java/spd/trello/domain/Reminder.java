package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Reminder extends Resource {
    private Date start;
    private Date finish;
    private Date remindOn;
    private Boolean active = Boolean.FALSE;
    private UUID cardId;
}
