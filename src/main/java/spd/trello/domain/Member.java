package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Member extends Resource{
    private UUID usersId;
    private Role role = Role.GUEST;
    private UUID workspaceId;

}
