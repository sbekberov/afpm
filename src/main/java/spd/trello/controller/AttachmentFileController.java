package spd.trello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domain.Attachment;
import spd.trello.service.AttachmentService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public class AttachmentFileController{

    @Autowired
    AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity saveAttachment(@RequestParam(value = "file") MultipartFile multipartFile,
                                         @RequestParam String name,
                                         @RequestParam(required = false) UUID cardId,
                                         @RequestParam String createdBy)
    {
        Attachment attachment = attachmentService.save(multipartFile, name, cardId,createdBy);
        return new ResponseEntity(attachment, HttpStatus.OK);
}


}