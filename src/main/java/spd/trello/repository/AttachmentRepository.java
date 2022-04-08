package spd.trello.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spd.trello.domain.Attachment;

import java.util.UUID;

@Repository
public interface AttachmentRepository extends AbstractRepository<Attachment> {
}