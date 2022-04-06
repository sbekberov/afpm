package spd.trello.domain.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttachmentDTO {
    private UUID id;
    private String createdBy = "bekberov@gmail.com";
    private LocalDateTime createdDate;
    private String name;
    private byte[] multiPartBytes;
    private UUID cardId;
    private boolean failed;
}
