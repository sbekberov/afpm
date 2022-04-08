package spd.trello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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
@RequestMapping("/attachments")
public class AttachmentController extends AbstractController<Attachment, AttachmentService> {
    public AttachmentController(AttachmentService service) {
        super(service);
    }

    @Autowired
    AttachmentService attachmentService;

    @Value("${app.saveToFile}")
    boolean saveToFile;


    @PostMapping("/upload")
    public ResponseEntity<Attachment> create(@RequestParam(value = "file") MultipartFile multipartFile,
                                             @RequestParam(required = false) UUID cardId,
                                             @RequestParam(value = "createdBy") String createdBy) {
        if (saveToFile) {
            return new ResponseEntity<>(attachmentService.saveToFile(multipartFile, cardId, createdBy), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(attachmentService.saveToDb(multipartFile, cardId, createdBy), HttpStatus.OK);
        }

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable UUID id) {
        Attachment attachment = attachmentService.load(id);
        return ResponseEntity.ok()
                .contentType(MediaType.MULTIPART_MIXED)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(new ByteArrayResource(attachment.getMultiPartBytes()));
    }

}




