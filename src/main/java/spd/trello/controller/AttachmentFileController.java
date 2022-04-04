package spd.trello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domain.dto.AttachmentDTO;
import spd.trello.service.AttachmentService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public class AttachmentFileController{

    @Autowired
    AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity saveAttachment(@RequestParam(value = "file") MultipartFile attachment,
                                         @RequestParam String name,
                                         @RequestParam String createdBy,
                                         @RequestParam(required = false) UUID cardId) {
        AttachmentDTO result = attachmentService.save(attachment, AttachmentDTO.builder()
                .name(name)
                .cardId(cardId)
                .createdBy(createdBy)
                .build());
        if (result.isFailed()) {
            return new ResponseEntity(result, HttpStatus.CONFLICT);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }
}