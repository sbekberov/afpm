package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends Resource{
    private Member member;
    private String content;
    private LocalDateTime date;
    private UUID cardId;
    private UUID usersId;

}
