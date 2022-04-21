package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardIntegrationTest extends AbstractIntegrationTest<Card> {
    private final String URL_TEMPLATE = "/cards";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        CardList cardList = helper.getNewCardList("create@CIT");
        Card card = new Card();
        card.setCreatedBy(cardList.getCreatedBy());
        card.setCardListId(cardList.getId());
        card.setName("name");
        Reminder reminder = new Reminder();
        reminder.setRemindOn(LocalDateTime.now());
        reminder.setStart(LocalDateTime.now());
        reminder.setFinish(LocalDateTime.now());
        card.setReminder(reminder);
        MvcResult mvcResult = super.create(URL_TEMPLATE, card);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(card.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(card.getCreatedDate().toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(card.getName(), getValue(mvcResult, "$.name")),
                () -> assertFalse((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertNotNull(getValue(mvcResult, "$.reminder"))
        );
    }

    @Test
    public void createFailure() throws Exception {
        Card entity = new Card();
        MvcResult mvcResult = super.create(URL_TEMPLATE, entity);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        Card firstCard = helper.getNewCard("1findAll@CIT");
        firstCard.setCreatedBy(firstCard.getCreatedBy());
        Card secondCard = helper.getNewCard("2findAll@CIT");
        secondCard.setCreatedBy(secondCard.getCreatedBy());
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<Card> testCards = helper.getCardsArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testCards.contains(firstCard)),
                () -> assertTrue(testCards.contains(secondCard))
        );
    }

    @Test
    public void findById() throws Exception {
        Card card = helper.getNewCard("findById@CIT");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, card.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(card.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(card.getCreatedDate().withNano(0).toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(card.getName(), getValue(mvcResult, "$.name")),
                () -> assertFalse((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(card.getCardListId().toString(), getValue(mvcResult, "$.cardListId")),
                () -> assertNotNull(getValue(mvcResult, "$.reminder"))
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        Card card = helper.getNewCard("deleteById@CIT");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, card.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<Card> testCards = helper.getCardsArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testCards.contains(card))
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
        Card card = helper.getNewCard("update@CIT");
        card.setUpdatedBy(card.getCreatedBy());
        card.setUpdatedDate(LocalDateTime.now().withNano(0));
        card.setName("new Name");
        card.setArchived(true);
        card.setDescription("new description");
        MvcResult mvcResult = super.update(URL_TEMPLATE, card.getId(), card);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(card.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(card.getCreatedDate().withNano(0).toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(card.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(card.getUpdatedDate().withNano(0).toString(), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(card.getName(), getValue(mvcResult, "$.name")),
                () -> assertTrue((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(card.getCardListId().toString(), getValue(mvcResult, "$.cardListId")),

                () -> assertNotNull(getValue(mvcResult, "$.reminder"))
        );
    }

    @Test
    public void updateFailure() throws Exception {
        Card firstCard = helper.getNewCard("1updateFailure");
        firstCard.setName(null);
        firstCard.setUpdatedBy(firstCard.getCreatedBy());

        Card secondCard = new Card();
        secondCard.setId(firstCard.getId());

        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstCard.getId(), firstCard);
        MvcResult secondMvcResult = super.update(URL_TEMPLATE, secondCard.getId(), secondCard);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), secondMvcResult.getResponse().getStatus())
        );
    }
}
