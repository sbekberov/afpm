package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Card;
import spd.trello.domain.Comment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentIntegrationTest extends AbstractIntegrationTest<Comment> {
    private final String URL_TEMPLATE = "/comments";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        Card card = helper.getNewCard("s.bekberov@gmail.com");
        Comment comment = new Comment();
        comment.setCreatedBy(card.getCreatedBy());
        comment.setCardId(card.getId());
        comment.setText("text");

        MvcResult mvcResult = super.create(URL_TEMPLATE, comment);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(comment.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(comment.getText(), getValue(mvcResult, "$.text")),
                () -> assertEquals(card.getId().toString(), getValue(mvcResult, "$.cardId"))
        );
    }

    @Test
    public void createFailure() throws Exception {
        Comment comment = new Comment();
        MvcResult mvcResult = super.create(URL_TEMPLATE, comment);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        Comment firstComment = helper.getNewComment("s.bekberov@gmail.com");
        Comment secondComment = helper.getNewComment(" bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<Comment> testCardLists = helper.getCommentsArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testCardLists.contains(firstComment)),
                () -> assertTrue(testCardLists.contains(secondComment))
        );
    }

    @Test
    public void findById() throws Exception {
        Comment comment = helper.getNewComment("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, comment.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(comment.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(comment.getText(), getValue(mvcResult, "$.text")),
                () -> assertEquals(comment.getCardId().toString(), getValue(mvcResult, "$.cardId"))
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        Comment comment = helper.getNewComment("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, comment.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<Comment> testComments = helper.getCommentsArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testComments.contains(comment))
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
        Comment comment = helper.getNewComment("s.bekberov@gmail.com");
        comment.setUpdatedBy(comment.getCreatedBy());
        comment.setText("new text");

        MvcResult mvcResult = super.update(URL_TEMPLATE, comment.getId(), comment);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(comment.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(comment.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(comment.getText(), getValue(mvcResult, "$.text")),
                () -> assertEquals(comment.getCardId().toString(), getValue(mvcResult, "$.cardId"))
        );
    }

    @Test
    public void updateFailure() throws Exception {
        Comment firstComment = helper.getNewComment("s.bekberov@gmail.com");
        firstComment.setText(null);
        firstComment.setUpdatedBy(firstComment.getCreatedBy());

        Comment secondComment = new Comment();
        secondComment.setId(firstComment.getId());

        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstComment.getId(), firstComment);
        MvcResult secondMvcResult = super.update(URL_TEMPLATE, secondComment.getId(), secondComment);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), secondMvcResult.getResponse().getStatus())
        );
    }
}
