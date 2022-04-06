package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domain.Attachment;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.AttachmentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class AttachmentService extends AbstractService<Attachment, AttachmentRepository> {
    @Autowired
    public AttachmentService(AttachmentRepository repository) {
        super(repository);
    }

    @Override
    public Attachment update(Attachment entity) {
        Attachment oldAttachment = findById(entity.getId());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setCreatedBy(oldAttachment.getCreatedBy());
        entity.setCreatedDate(oldAttachment.getCreatedDate());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getLink() == null && entity.getName() == null) {
            throw new ResourceNotFoundException();
        }

        if (oldAttachment.getCardId() != null) {
            entity.setCardId(oldAttachment.getCardId());
        }

        if (entity.getName() == null) {
            entity.setName(oldAttachment.getName());
        }
        if (entity.getLink() == null) {
            entity.setLink(oldAttachment.getLink());
        }
        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Attachment save(MultipartFile multipartFile, String name, UUID cardId,String createdBy) {
        Attachment attachment = new Attachment();

        try {
            attachment.setMultiPartBytes(multipartFile.getBytes());
            attachment.setName(name);
            attachment.setCardId(cardId);
            attachment.setCreatedBy(createdBy);
            attachment.setCreatedDate(LocalDateTime.now());


        } catch (Exception e) {
            e.printStackTrace();
            return attachment;
        }
        return repository.save(attachment);
    }
}


