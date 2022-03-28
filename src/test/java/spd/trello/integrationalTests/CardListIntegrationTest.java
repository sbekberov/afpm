package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.*;
import spd.trello.domain.enums.BoardVisibility;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardListIntegrationTest extends AbstractIntegrationTest<CardList> {
    private final String URL_TEMPLATE = "/cardLists";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        Board board = helper.getNewBoard("s.bekberov@gmail.com");
        CardList cardList = new CardList();
        cardList.setCreatedBy(board.getCreatedBy());
        cardList.setDescription("CardList description");
        cardList.setBoardId(board.getId());
        cardList.setName("CardList");
        cardList.setArchived(true);
        MvcResult mvcResult = super.create(URL_TEMPLATE, cardList);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(cardList.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(cardList.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(cardList.getDescription(), getValue(mvcResult, "$.description")),
                () -> assertTrue((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(board.getId().toString(), getValue(mvcResult, "$.boardId"))
        );
    }

    @Test
    public void createFailure() throws Exception {
        CardList entity = new CardList();
        MvcResult mvcResult = super.create(URL_TEMPLATE, entity);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        CardList firstCardList = helper.getNewCardList("s.bekberov@gmail.com");
        CardList secondCardList = helper.getNewCardList("bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<CardList> testCardLists = helper.getCardListsArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testCardLists.contains(firstCardList)),
                () -> assertTrue(testCardLists.contains(secondCardList))
        );
    }

    @Test
    public void findById() throws Exception {
        CardList cardList = helper.getNewCardList("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, cardList.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(cardList.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(cardList.getName(), getValue(mvcResult, "$.name")),
                () -> assertFalse((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(cardList.getBoardId().toString(), getValue(mvcResult, "$.boardId"))
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        CardList cardList = helper.getNewCardList("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, cardList.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<CardList> testCardLists = helper.getCardListsArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testCardLists.contains(cardList))
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
        CardList cardList = helper.getNewCardList("s.bekberov@gmail.com");
        cardList.setUpdatedBy(cardList.getCreatedBy());
        cardList.setName("new Name");
        cardList.setArchived(true);
        MvcResult mvcResult = super.update(URL_TEMPLATE, cardList.getId(), cardList);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(cardList.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(cardList.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(cardList.getName(), getValue(mvcResult, "$.name")),
                () -> assertTrue((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(cardList.getBoardId().toString(), getValue(mvcResult, "$.boardId"))
        );
    }

    @Test
    public void updateFailure() throws Exception {
        CardList firstCardList = helper.getNewCardList("s.bekberov@gmail.com");
        firstCardList.setName(null);
        firstCardList.setUpdatedBy(firstCardList.getCreatedBy());

        CardList secondCardList = new CardList();
        secondCardList.setId(firstCardList.getId());

        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstCardList.getId(), firstCardList);
        MvcResult secondMvcResult = super.update(URL_TEMPLATE, secondCardList.getId(), secondCardList);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), secondMvcResult.getResponse().getStatus())
        );
    }
}
