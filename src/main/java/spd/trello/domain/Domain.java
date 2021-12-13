package spd.trello.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Domain {
    UUID id = UUID.randomUUID();
}
