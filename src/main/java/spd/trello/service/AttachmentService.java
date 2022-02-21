package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Attachment;
import spd.trello.repository.CRUDRepository;

@Service
public class AttachmentService extends AbstractService<Attachment> {

    public AttachmentService(CRUDRepository<Attachment> attachmentRepository) {
        super(attachmentRepository);
    }
}

