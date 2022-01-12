package spd.trello.service;

import spd.trello.domain.Attachment;
import spd.trello.domain.Member;
import spd.trello.repository.AttachmentRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class AttachmentService extends AbstractService<Attachment>{

    AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) throws SQLException, IOException {
        super();
        this.attachmentRepository = attachmentRepository;
    }

    public AttachmentService() throws SQLException, IOException {
        super();
        attachmentRepository=new AttachmentRepository(dataSource);
    }


    public Attachment create(Member member, String link, String name, UUID commentId, UUID cardId) throws IllegalAccessException {
        Attachment attachment = new Attachment();
        attachment.setId(UUID.randomUUID());
        attachment.setLink(link);
        attachment.setName(name );
        attachment.setCreatedBy(member.getCreatedBy());
        attachment.setCreatedDate(Date.valueOf(LocalDate.now()));
        attachment.setCommentId(commentId);
        attachment.setCardId(cardId);
        attachmentRepository.create(attachment);
        return attachmentRepository.findById(attachment.getId());
    }


    public void update(Attachment attachment) {
        attachmentRepository.update(attachment);
    }

    public void getAll() {
        attachmentRepository.getAll();
    }


    public Attachment findById(UUID id) {
        Attachment attachment = null;
        try {
            attachment = attachmentRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return attachment;
    }


    public boolean delete(UUID id) {
        return attachmentRepository.delete(id);
    }
}
