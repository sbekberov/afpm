package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import spd.trello.domain.Card;
import spd.trello.domain.CheckList;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CheckListIntegrationTest extends AbstractIntegrationTest<CheckList> {
    private final String URL_TEMPLATE = "/checkLists";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        Card card = helper.getNewCard("s.bekberov@gmail.com");
        CheckList checkList = new CheckList();
        checkList.setCreatedBy(card.getCreatedBy());
        checkList.setName("name");
        checkList.setCardId(card.getId());

        MvcResult mvcResult = super.create(URL_TEMPLATE, checkList);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(checkList.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(checkList.getCardId().toString(), getValue(mvcResult, "$.cardId")),
                () -> assertNotNull(getValue(mvcResult, "$.items"))
        );
    }

    @Test
    public void createFailure() throws Exception {
        CheckList checkList = new CheckList();
        MvcResult mvcResult = super.create(URL_TEMPLATE, checkList);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        CheckList firstChecklist = helper.getNewСheckList("s.bekberov@gmail.com");
        CheckList secondChecklist = helper.getNewСheckList("bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<CheckList> testCheckLists = helper.getCheckListArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testCheckLists.contains(firstChecklist)),
                () -> assertTrue(testCheckLists.contains(secondChecklist))
        );
    }

    @Test
    @Transactional
    public void findById() throws Exception {
        CheckList checklist = helper.getNewСheckList("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, checklist.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(checklist.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(checklist.getCardId().toString(), getValue(mvcResult, "$.cardId")),
                () -> assertNotNull(getValue(mvcResult, "$.items"))
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Transactional
    public void deleteById() throws Exception {
        CheckList checklist = helper.getNewСheckList("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, checklist.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<CheckList> testChecklists = helper.getCheckListArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testChecklists.contains(checklist))
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
        CheckList checkList = helper.getNewСheckList("s.bekberov@gmail.com");
        checkList.setName("newName");
        checkList.setUpdatedBy(checkList.getCreatedBy());
        MvcResult mvcResult = super.update(URL_TEMPLATE, checkList.getId(), checkList);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(checkList.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(checkList.getCardId().toString(), getValue(mvcResult, "$.cardId")),
                () -> assertNotNull(getValue(mvcResult, "$.items"))
        );
    }

    @Test
    public void updateFailure() throws Exception {
        CheckList checkList = helper.getNewСheckList("s.bekberov@gmail.com");
        checkList.setName(null);
        checkList.setCardId(null);

        MvcResult mvcResult = super.update(URL_TEMPLATE, checkList.getId(), checkList);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }
}
