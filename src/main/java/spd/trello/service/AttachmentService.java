package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domain.Attachment;
import spd.trello.domain.dto.AttachmentDTO;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.AttachmentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


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
    public AttachmentDTO save(MultipartFile multipartFile, AttachmentDTO attachmentDTO) {
        byte[] multiPartBytes;
        Attachment attachment = new Attachment();
        try {
            multiPartBytes = multipartFile.getBytes();
            attachment.setMultiPartBytes(multiPartBytes);
            attachment.setCreatedBy(attachmentDTO.getCreatedBy());
            attachment.setCreatedDate(LocalDateTime.now());
            attachment.setName(attachmentDTO.getName());
            attachment.setCardId(attachmentDTO.getCardId());
            repository.save(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return AttachmentDTO.builder()
                    .id(attachment.getId())
                    .cardId(attachment.getCardId())
                    .name(attachment.getName())
                    .createdBy(attachment.getCreatedBy())
                    .createdDate(attachment.getCreatedDate())
                    .failed(true)
                    .build();
        }
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .cardId(attachment.getCardId())
                .name(attachment.getName())
                .createdBy(attachment.getCreatedBy())
                .createdDate(attachment.getCreatedDate())
                .failed(false)
                .build();
    }
}


