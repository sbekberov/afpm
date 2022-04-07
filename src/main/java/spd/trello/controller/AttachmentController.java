package spd.trello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domain.Attachment;
import spd.trello.exception.BadRequestException;
import spd.trello.service.AttachmentService;
import org.springframework.core.io.Resource;

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
                                             @RequestParam String name,
                                             @RequestParam(required = false) UUID cardId,
                                             @RequestParam String createdBy) {
        if (saveToFile) {
            try {
                Attachment attachment = attachmentService.saveToFile(multipartFile, name, cardId, createdBy);
                return new ResponseEntity<>(attachment, HttpStatus.OK);
            } catch (RuntimeException exception) {
                throw new BadRequestException(exception.getMessage());
            }
        } else {
            try {
                Attachment attachment = attachmentService.saveToDb(multipartFile, name, cardId, createdBy);
                return new ResponseEntity(attachment, HttpStatus.OK);
            } catch (RuntimeException exception) {
                throw new BadRequestException(exception.getMessage());
            }
        }

    }

    public ResponseEntity<byte[]> getFileFromDb(@PathVariable UUID id) {
        Attachment attachment = attachmentService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(attachment.getMultiPartBytes());

    }
    @ResponseBody
    public ResponseEntity<Resource> getFileFromFile(@PathVariable UUID id) {
        Resource file = attachmentService.load(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    @GetMapping("/download/{id}")
   public void chooseDownloading(@PathVariable UUID id){
        if(saveToFile){
            getFileFromFile(id);
        }else {
            getFileFromDb(id);
        }
   }
}




