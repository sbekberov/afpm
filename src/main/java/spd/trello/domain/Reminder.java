package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import spd.trello.domain.common.Domain;


import javax.persistence.*;
import java.sql.Date;


@Getter
@Setter
@Entity
@Table(name = "reminder")
public class Reminder extends Domain {
    @Column(name = "start")
    private Date start;
    @Column(name = "finish")
    private Date finish;
    @Column(name = "remind_on")
    private Date remindOn;
    @Column(name = "active")
    private Boolean active = Boolean.FALSE;
    @OneToOne (mappedBy = "reminder", cascade = CascadeType.ALL)
    @JsonIgnore
    private Card card;
}
