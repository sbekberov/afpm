package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Attachment;
import spd.trello.domain.Card;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AttachmentIntegrationTest extends AbstractIntegrationTest<Attachment> {
    private final String URL_TEMPLATE = "/attachments";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        Card card = helper.getNewCard("s.bekberov@gmail.com");
        Attachment attachment = new Attachment();
        attachment.setCreatedBy(card.getCreatedBy());
        attachment.setName("name");
        attachment.setLink("link");
        attachment.setCardId(card.getId());
        MvcResult mvcResult = super.create(URL_TEMPLATE, attachment);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(attachment.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(LocalDateTime.now(), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(attachment.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(attachment.getLink(), getValue(mvcResult, "$.link")),
                () -> assertEquals(card.getId().toString(), getValue(mvcResult, "$.cardId"))
        );
    }

    @Test
    public void createFailure() throws Exception {
        Attachment attachment = new Attachment();
        MvcResult mvcResult = super.create(URL_TEMPLATE, attachment);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        Attachment firstAttachment = helper.getNewAttachment("s.bekberov@gmail.com");
        Attachment secondAttachment = helper.getNewAttachment("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<Attachment> testAttachments = helper.getAttachmentsArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testAttachments.contains(firstAttachment)),
                () -> assertTrue(testAttachments.contains(secondAttachment))
        );
    }

    @Test
    public void findById() throws Exception {
        Attachment attachment = helper.getNewAttachment("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, attachment.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(attachment.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(LocalDateTime.now(), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(attachment.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(attachment.getLink(), getValue(mvcResult, "$.link")),
                () -> assertEquals(attachment.getCardId().toString(), getValue(mvcResult, "$.cardId"))
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        Attachment attachment = helper.getNewAttachment("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, attachment.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<Attachment> testAttachments = helper.getAttachmentsArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testAttachments.contains(attachment))
        );
    }

    @Test
    public void deleteByIdFailure() throws Exception {
        MvcResult mvcResult = super.delete(URL_TEMPLATE, UUID.randomUUID());

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus())
        );
    }

    @Test
    public void update() throws Exception {
        Attachment attachment = helper.getNewAttachment("s.bekberov@gmail.com");
        attachment.setUpdatedBy(attachment.getCreatedBy());
        attachment.setName("new named");
        attachment.setLink("new link");

        MvcResult mvcResult = super.update(URL_TEMPLATE, attachment.getId(), attachment);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(attachment.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(LocalDateTime.now(), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(attachment.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(LocalDateTime.now(), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(attachment.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(attachment.getLink(), getValue(mvcResult, "$.link")),
                () -> assertEquals(attachment.getCardId().toString(), getValue(mvcResult, "$.cardId"))
        );
    }

    @Test
    public void updateFailure() throws Exception {
        Attachment firstAttachment = helper.getNewAttachment("s.bekberov@gmail.com");
        firstAttachment.setName(null);
        firstAttachment.setLink(null);
        firstAttachment.setUpdatedBy(firstAttachment.getCreatedBy());

        Attachment secondAttachment = new Attachment();
        secondAttachment.setId(firstAttachment.getId());

        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstAttachment.getId(), firstAttachment);
        MvcResult secondMvcResult = super.update(URL_TEMPLATE, secondAttachment.getId(), secondAttachment);

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), secondMvcResult.getResponse().getStatus())
        );
    }
}
