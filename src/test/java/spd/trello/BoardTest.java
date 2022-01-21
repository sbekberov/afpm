package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.*;
import spd.trello.repository.BoardRepository;
import spd.trello.service.BoardService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static spd.trello.Helper.*;

public class BoardTest extends BaseTest{

    private final BoardService service;

    public BoardTest() {
        this.service = new BoardService(new BoardRepository(dataSource));
    }
    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board testBoard = service.create(member, workspace.getId(), "test", "test");
        assertNotNull(testBoard);
        assertAll(
                () -> assertEquals("test@gmail", testBoard.getCreatedBy()),
                () -> assertNull(testBoard.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testBoard.getCreatedDate()),
                () -> assertNull(testBoard.getUpdatedDate()),
                () -> assertEquals("test", testBoard.getName()),
                () -> assertEquals("test", testBoard.getDescription()),
                () -> assertEquals(BoardVisibility.PRIVATE, testBoard.getVisibility()),
                () -> assertFalse(testBoard.getArchived()),
                () -> assertEquals(workspace.getId(), testBoard.getWorkspaceId())
        );
    }
    @Test
    public void testFindAll() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board testFirstBoard = service.create(member, workspace.getId(), "firstBoard", "firstDescription");
        Board testSecondBoard = service.create(member, workspace.getId(), "secondBoard", "secondDescription");
        assertNotNull(testFirstBoard);
        assertNotNull(testSecondBoard);
        List<Board> testBoard = service.getAll();
        assertAll(
                () -> assertTrue(testBoard.contains(testFirstBoard)),
                () -> assertTrue(testBoard.contains(testSecondBoard))
        );
    }
    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(id),
                "no exception"
        );
        assertEquals("Board with ID: " + id + " doesn't exists", ex.getMessage());
    }

    @Test
    public void testDelete() {
        User user = getNewUser("test@mail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board testBoard = service.create(member, workspace.getId(), "Board", "Description");
        assertNotNull(testBoard);
        UUID id = testBoard.getId();
        assertAll(
                () -> assertTrue(service.delete(id)),
                () -> assertFalse(service.delete(id))
        );
    }
    @Test
    public void testUpdate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = service.create(member, workspace.getId(), "test", "test");
        assertNotNull(board);
        board.setName("newTest");
        board.setDescription("newTest");
        board.setVisibility(BoardVisibility.PUBLIC);
        board.setArchived(true);
        Board testBoard = service.update(member, board);
        assertAll(
                () -> assertEquals("test@gmail", testBoard.getCreatedBy()),
                () -> assertEquals("test@gmail", testBoard.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testBoard.getCreatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testBoard.getUpdatedDate()),
                () -> assertEquals("newTest", testBoard.getName()),
                () -> assertEquals("newTest", testBoard.getDescription()),
                () -> assertEquals(BoardVisibility.PUBLIC, testBoard.getVisibility()),
                () -> assertTrue(testBoard.getArchived()),
                () -> assertEquals(workspace.getId(), testBoard.getWorkspaceId())
        );
    }
}

