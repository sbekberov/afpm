package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.BoardTemplate;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTemplateIntegrationTest extends AbstractIntegrationTest<BoardTemplate> {
    private final String URL_TEMPLATE = "/boardsTemplates";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        BoardTemplate boardTemplate = new BoardTemplate();
        boardTemplate.setName("BoardTemplate");
        boardTemplate.setDescription("test desk");
        boardTemplate.setCreatedDate(LocalDateTime.now());
        boardTemplate.setCreatedBy("ssss");
        MvcResult firstMvcResult = super.create(URL_TEMPLATE, boardTemplate);




        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(firstMvcResult, "$.id")),
                () -> assertEquals(boardTemplate.getName(), getValue(firstMvcResult, "$.name")),
                () -> assertEquals(boardTemplate.getDescription(), getValue(firstMvcResult, "$.description")));
    }

    @Test
    public void createFailure() throws Exception {
        BoardTemplate boardTemplate = new BoardTemplate();
        MvcResult mvcResult = super.create(URL_TEMPLATE, boardTemplate);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        BoardTemplate firsTemplate = helper.getNewBoardTemplate("1 template" , " test test");
        BoardTemplate secondTemplate = helper.getNewBoardTemplate("2 template" , " test");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<BoardTemplate> testBoardTemplates = helper.getBoardTemplatesArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testBoardTemplates .contains(firsTemplate)),
                () -> assertTrue(testBoardTemplates .contains(secondTemplate))
        );
    }

    @Test
    public void findById() throws Exception {
        BoardTemplate boardTemplate = helper.getNewBoardTemplate("BBBB", "AAAA");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, boardTemplate.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(boardTemplate.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(boardTemplate.getDescription(), getValue(mvcResult, "$.description")));
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        BoardTemplate boardTemplate = helper.getNewBoardTemplate("CCCCCC" , "DDDDDD");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, boardTemplate.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<BoardTemplate> testBoardTemplate = helper.getBoardTemplatesArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testBoardTemplate.contains(boardTemplate)));
    }

    @Test
    public void deleteByIdFailure() throws Exception {
        MvcResult mvcResult = super.delete(URL_TEMPLATE, UUID.randomUUID());

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus()));
    }

    @Test
    public void update() throws Exception {
       BoardTemplate boardTemplate = helper.getNewBoardTemplate("FFFF" , "GGGGG");
        boardTemplate.setName("New name");
        boardTemplate.setDescription("new desct");
        boardTemplate.setCreatedDate(LocalDateTime.now());
        boardTemplate.setCreatedBy("ssss");
        boardTemplate.setUpdatedBy("sssss");
        MvcResult mvcResult = super.update(URL_TEMPLATE, boardTemplate.getId(), boardTemplate);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(boardTemplate.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(boardTemplate.getDescription(), getValue(mvcResult, "$.description")));
    }

    @Test
    public void updateFailure() throws Exception {
       BoardTemplate boardTemplate = helper.getNewBoardTemplate("FFFF" , "BBB");
        BoardTemplate updateBoardTemplate = new BoardTemplate();
        boardTemplate.setId(boardTemplate.getId());
        MvcResult mvcResult = super.update(URL_TEMPLATE, updateBoardTemplate.getId(), updateBoardTemplate);
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }
}
