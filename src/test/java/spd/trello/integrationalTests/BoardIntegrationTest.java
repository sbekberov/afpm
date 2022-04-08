package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Board;
import spd.trello.domain.Member;
import spd.trello.domain.Workspace;
import spd.trello.domain.enums.BoardVisibility;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardIntegrationTest extends AbstractIntegrationTest<Board> {
    private final String URL_TEMPLATE = "/boards";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        Workspace workspace = helper.getNewWorkspace("s.bekberov@gmail.com");
        Board board = new Board();
        board.setCreatedBy(workspace.getCreatedBy());
        board.setName("Board");
        board.setWorkspaceId(workspace.getId());
        Set<UUID> membersIds = new HashSet<>();
        membersIds.add(workspace.getMembersIds().iterator().next());
        board.setMembersIds(membersIds);
        MvcResult firstMvcResult = super.create(URL_TEMPLATE, board);
        Set<UUID> testFirstMembersIds = helper.getIdsFromJson(getValue(firstMvcResult, "$.membersIds").toString());



        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(firstMvcResult, "$.id")),
                () -> assertEquals(board.getCreatedBy(), getValue(firstMvcResult, "$.createdBy")),
                () -> assertEquals(board.getCreatedDate().withNano(0).toString(), getValue(firstMvcResult, "$.createdDate")),
                () -> assertNull(getValue(firstMvcResult, "$.updatedBy")),
                () -> assertNull(getValue(firstMvcResult, "$.updatedDate")),
                () -> assertEquals(board.getName(), getValue(firstMvcResult, "$.name")),
                () -> assertNull(getValue(firstMvcResult, "$.description")),
                () -> assertEquals(board.getVisibility().toString(), getValue(firstMvcResult, "$.visibility")),
                () -> assertFalse((Boolean) getValue(firstMvcResult, "$.archived")),
                () -> assertEquals(workspace.getId().toString(), getValue(firstMvcResult, "$.workspaceId")),
                () -> assertTrue(testFirstMembersIds.contains(workspace.getMembersIds().iterator().next())),
                () -> assertEquals(1, testFirstMembersIds.size())


        );
    }

    @Test
    public void createFailure() throws Exception {
        Board entity = new Board();
        MvcResult mvcResult = super.create(URL_TEMPLATE, entity);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        Board firstBoard = helper.getNewBoard("s.bekberov@gmail.com");
        Board secondBoard = helper.getNewBoard("bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<Board> testWorkspaces = helper.getBoardsArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testWorkspaces.contains(firstBoard)),
                () -> assertTrue(testWorkspaces.contains(secondBoard))
        );
    }

    @Test
    public void findById() throws Exception {
        Board board = helper.getNewBoard("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, board.getId());
        Set<UUID> testMembersIds = helper.getIdsFromJson(getValue(mvcResult, "$.membersIds").toString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(board.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(LocalDateTime.now().withNano(0).toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(board.getName(), getValue(mvcResult, "$.name")),
                () -> assertNull(getValue(mvcResult, "$.description")),
                () -> assertEquals(board.getVisibility().toString(), getValue(mvcResult, "$.visibility")),
                () -> assertFalse((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(board.getWorkspaceId().toString(), getValue(mvcResult, "$.workspaceId")),
                () -> assertTrue(testMembersIds.contains(board.getMembersIds().iterator().next())),
                () -> assertEquals(1, testMembersIds.size())
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        Board board = helper.getNewBoard("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, board.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<Board> testBoards = helper.getBoardsArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testBoards.contains(board))
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
        Board board = helper.getNewBoard("s.bekberov@gmail.com");
        Member secondMember = helper.getNewMember("bekberov@gmail.com");
        board.setUpdatedBy(board.getCreatedBy());
        board.setUpdatedDate(LocalDateTime.now().withNano(0));
        board.setName("Updated Board");
        board.setDescription("Updated description");
        board.setVisibility(BoardVisibility.PUBLIC);
        board.setArchived(true);
        Set<UUID> membersIds = board.getMembersIds();
        membersIds.add(secondMember.getId());
        board.setMembersIds(membersIds);
        MvcResult mvcResult = super.update(URL_TEMPLATE, board.getId(), board);
        Set<UUID> testMembersIds = helper.getIdsFromJson(getValue(mvcResult, "$.membersIds").toString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(board.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(board.getCreatedDate().withNano(0).toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(board.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(board.getUpdatedDate().withNano(0).toString(), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(board.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(board.getDescription(), getValue(mvcResult, "$.description")),
                () -> assertEquals(board.getVisibility().toString(), getValue(mvcResult, "$.visibility")),
                () -> assertTrue((Boolean) getValue(mvcResult, "$.archived")),
                () -> assertEquals(board.getWorkspaceId().toString(), getValue(mvcResult, "$.workspaceId")),
                () -> assertTrue(testMembersIds.contains(membersIds.iterator().next())),
                () -> assertTrue(testMembersIds.contains(membersIds.iterator().next())),
                () -> assertEquals(2, testMembersIds.size())
        );
    }

    @Test
    public void updateFailure() throws Exception {
        Board firstBoard = helper.getNewBoard("s.bekberov@gmail.com");
        firstBoard.setName(null);
        firstBoard.setUpdatedBy(firstBoard.getCreatedBy());

        Board secondBoard = new Board();
        secondBoard.setId(firstBoard.getId());

        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstBoard.getId(), firstBoard);
        MvcResult secondMvcResult = super.update(URL_TEMPLATE, secondBoard.getId(), secondBoard);

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), secondMvcResult.getResponse().getStatus())
        );
    }
}
