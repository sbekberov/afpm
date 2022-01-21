package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.*;
import spd.trello.repository.CheckListRepository;
import spd.trello.service.CheckListService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spd.trello.Helper.*;
import static spd.trello.Helper.getNewCard;

public class CheckListTest extends BaseTest{

    private final CheckListService service;

    public CheckListTest() {
        this.service = new CheckListService(new CheckListRepository(dataSource));
    }
    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Checklist testChecklist = service.create(member, "test", card.getId());
        assertNotNull(testChecklist);
        assertAll(
                () -> assertEquals("test@gmail", testChecklist.getCreatedBy()),
                () -> assertNull(testChecklist.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testChecklist.getCreatedDate()),
                () -> assertEquals (Date.valueOf(LocalDate.now()),testChecklist.getUpdatedDate()),
                () -> assertEquals("test", testChecklist.getName()),
                () -> assertEquals(card.getId(), testChecklist.getCardId())
        );
    }

    @Test
    public void findAll() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Checklist testFirstChecklist = service.create(member, "firstChecklist", card.getId());
        Checklist testSecondChecklist = service.create(member,"secondChecklist", card.getId());
        assertNotNull(testFirstChecklist);
        assertNotNull(testSecondChecklist);
        List<Checklist> testComments = service.getAll();
        assertAll(
                () -> assertTrue(testComments.contains(testFirstChecklist)),
                () -> assertTrue(testComments.contains(testSecondChecklist))
        );
    }
    @Test
    public void createFailure() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.create(member, "name", null),
                "expected to throw  IllegalStateException, but it didn't"
        );
        assertEquals("Error CheckListRepository create", ex.getMessage());
    }
    @Test
    public void findById() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(id),
                "no exception"
        );
        assertEquals("Checklist with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void delete() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Checklist testChecklist = service.create(member,"test",card.getId());
        assertNotNull(testChecklist);
        UUID id = testChecklist.getId();
        assertAll(
                () -> assertTrue(service.delete(id)),
                () -> assertFalse(service.delete(id))
        );
    }
    @Test
    public void update() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Checklist checklist = service.create(member,"test",card.getId());
        assertNotNull(checklist);
        checklist.setName("newTest");
        Checklist testChecklist = service.update(member, checklist);
        assertAll(
                () -> assertEquals("test@gmail", testChecklist.getCreatedBy()),
                () -> assertEquals("test@gmail", testChecklist.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testChecklist.getCreatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testChecklist.getUpdatedDate()),
                () -> assertEquals("newTest", testChecklist.getName())
        );
    }
    @Test
    public void updateFailure() {
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setCreatedBy("selim");
        Checklist testChecklist = new Checklist();
        UUID id =UUID.randomUUID();
        testChecklist.setId(id);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.update(member, testChecklist),
                "expected to throw Illegal state exception, but it didn't"
        );
        assertEquals("Checklist with ID: " + id + " doesn't exists", ex.getMessage());
    }
}
