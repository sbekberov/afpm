package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import spd.trello.domain.common.Domain;


import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.sql.Date;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "reminder")
public class Reminder extends Domain implements Comparable<Reminder> {
    @Column(name = "start")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime start;

    @Column(name = "finish")
    @FutureOrPresent(message = "The finish field must be present or future.")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime finish;

    @Column(name = "remind_on")
    @FutureOrPresent(message = "The remindOn field must be present or future.")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime remindOn;

    @Column(name = "active")
    private Boolean active = Boolean.FALSE;
    @OneToOne (mappedBy = "reminder", cascade = CascadeType.ALL)
    @JsonIgnore
    private Card card;

    @Override
    public int compareTo(Reminder o) {
        return 0;
    }
}
