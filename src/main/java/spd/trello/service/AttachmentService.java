package spd.trello.service;

import spd.trello.domain.Attachment;
import spd.trello.domain.Member;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AttachmentService extends AbstractService<Attachment>{

    public AttachmentService (CRUDRepository<Attachment> attachmentRepository){
        super(attachmentRepository);
    }


    public Attachment create(Member member, String link, String name, UUID commentId, UUID cardId)  {
        Attachment attachment = new Attachment();
        attachment.setId(UUID.randomUUID());
        attachment.setLink(link);
        attachment.setName(name );
        attachment.setCreatedBy(member.getCreatedBy());
        attachment.setCreatedDate(Date.valueOf(LocalDate.now()));
        attachment.setCommentId(commentId);
        attachment.setCardId(cardId);
        repository.create(attachment);
        return repository.findById(attachment.getId());
    }


    public Attachment update(Attachment attachment) {
        return repository.update(attachment);
    }

    public List<Attachment> getAll() {
        return repository.getAll();
    }


    public Attachment findById(UUID id) {
        return repository.findById(id);
    }


    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
