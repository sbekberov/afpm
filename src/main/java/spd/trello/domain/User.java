package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Domain{
    private String firstName;
    private String lastName;
    private String email;
    private String timeZone;

}
