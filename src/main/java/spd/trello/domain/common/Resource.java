package spd.trello.domain.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;
import java.time.LocalDate;


@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class Resource extends Domain  {
    @Column(name = "created_by")
    String createdBy;
    @Column(name = "updated_by")
    String updatedBy;
    @Column(name = "created_date")
    Date createdDate=Date.valueOf(LocalDate.now());
    @Column(name = "updated_date")
    Date updatedDate;
}
