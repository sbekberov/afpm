package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Color extends Resource {
    private Integer red = 0;
    private Integer green = 0;
    private Integer blue = 0;
}
