package spd.trello.domain.common;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;
import java.time.LocalDate;


@Getter
@Setter
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
