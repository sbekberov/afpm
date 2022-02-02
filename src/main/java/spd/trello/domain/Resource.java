package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;


@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Resource extends Domain  {
    String createdBy;
    String updatedBy;
    Date createdDate=Date.valueOf(LocalDate.now());
    Date updatedDate;
}
