package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.BoardTemplate;
import spd.trello.domain.CardTemplate;



import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CardTemplateIntegrationTest extends AbstractIntegrationTest<CardTemplate> {
    private final String URL_TEMPLATE = "/cardsTemplates";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        CardTemplate cardTemplate = new CardTemplate();
        cardTemplate.setName("CardTemplate");
        cardTemplate.setDescription("test desk");
        cardTemplate.setCreatedDate(Date.valueOf(LocalDate.now()));
        cardTemplate.setCreatedBy("ssss");
        MvcResult firstMvcResult = super.create(URL_TEMPLATE, cardTemplate);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(firstMvcResult, "$.id")),
                () -> assertEquals(cardTemplate.getName(), getValue(firstMvcResult, "$.name")),
                () -> assertEquals(cardTemplate.getDescription(), getValue(firstMvcResult, "$.description")));
    }

    @Test
    public void createFailure() throws Exception {
        CardTemplate cardTemplate = new CardTemplate();
        MvcResult mvcResult = super.create(URL_TEMPLATE, cardTemplate);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        CardTemplate firsTemplate = helper.getNewCardTemplate("1 template" , " test test");
        CardTemplate secondTemplate = helper.getNewCardTemplate("2 template" , " test");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<CardTemplate> testCardTemplates = helper.getCardTemplatesArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testCardTemplates .contains(firsTemplate)),
                () -> assertTrue(testCardTemplates .contains(secondTemplate))
        );
    }

    @Test
    public void findById() throws Exception {
        CardTemplate cardTemplate = helper.getNewCardTemplate("BBBB", "AAAA");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, cardTemplate.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(cardTemplate.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(cardTemplate.getDescription(), getValue(mvcResult, "$.description")));
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        CardTemplate cardTemplate = helper.getNewCardTemplate("CCCCCC" , "DDDDDD");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, cardTemplate.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<CardTemplate> testCardTemplate = helper.getCardTemplatesArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testCardTemplate.contains(cardTemplate)));
    }

    @Test
    public void deleteByIdFailure() throws Exception {
        MvcResult mvcResult = super.delete(URL_TEMPLATE, UUID.randomUUID());

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus()));
    }

    @Test
    public void update() throws Exception {
        CardTemplate cardTemplate = helper.getNewCardTemplate("FFFF" , "GGGGG");
        cardTemplate.setName("New name");
        cardTemplate.setDescription("new desct");
        cardTemplate.setCreatedDate(Date.valueOf(LocalDate.now()));
        cardTemplate.setCreatedBy("ssss");
        cardTemplate.setUpdatedBy("sssss");
        MvcResult mvcResult = super.update(URL_TEMPLATE, cardTemplate.getId(), cardTemplate);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(cardTemplate.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(cardTemplate.getDescription(), getValue(mvcResult, "$.description")));
    }

    @Test
    public void updateFailure() throws Exception {
        CardTemplate cardTemplate = helper.getNewCardTemplate("FFFF" , "BBB");
        CardTemplate updateCardTemplate = new CardTemplate();
        cardTemplate.setId(cardTemplate.getId());
        MvcResult mvcResult = super.update(URL_TEMPLATE, updateCardTemplate.getId(), updateCardTemplate);
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }
}
