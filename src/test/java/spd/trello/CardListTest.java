package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.*;
import spd.trello.repository.CardListRepository;
import spd.trello.service.CardListService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static spd.trello.Helper.*;
import static spd.trello.Helper.getNewBoard;

public class CardListTest extends BaseTest{
    private final CardListService service;

    public CardListTest() {
       this.service = new CardListService(new CardListRepository(dataSource));
    }
    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList testCardList = service.create(member, board.getId(), "test");
        assertNotNull(testCardList);
        assertAll(
                () -> assertEquals("test@gmail", testCardList.getCreatedBy()),
                () -> assertNull(testCardList.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testCardList.getCreatedDate()),
                () -> assertNull(testCardList.getUpdatedDate()),
                () -> assertEquals("test", testCardList.getName()),
                () -> assertFalse(testCardList.getArchived()),
                () -> assertEquals(board.getId(), testCardList.getBoardId())
        );
    }
    @Test
    public void testFindAll() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList testFirstCardList = service.create(member, board.getId(), "firstCardList");
        CardList testSecondCardList = service.create(member, board.getId(), "secondCardList");
        assertNotNull(testFirstCardList);
        assertNotNull(testSecondCardList);
        List<CardList> testCardLists = service.getAll();
        assertAll(
                () -> assertTrue(testCardLists.contains(testFirstCardList)),
                () -> assertTrue(testCardLists.contains(testSecondCardList))
        );
    }
    @Test
    public void createFailure() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> service.create(member, null, "test"),
                "expected to throw  IllegalStateException, but it didn't"
        );
        assertEquals("Cannot invoke \"String.length()\" because \"name\" is null", ex.getMessage());
    }
    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(id),
                "no exception"
        );
        assertEquals("CardList with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void testDelete() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList testCardList = service.create(member,board.getId(), "test");
        assertNotNull(testCardList);
        UUID id = testCardList.getId();
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
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = service.create(member, board.getId(), "test");
        assertNotNull(cardList);
        cardList.setName("newTest");
        cardList.setArchived(true);
        CardList testCardList = service.update(member, cardList);
        assertAll(
                () -> assertEquals("test@gmail", testCardList.getCreatedBy()),
                () -> assertEquals("test@gmail", testCardList.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testCardList.getCreatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testCardList.getUpdatedDate()),
                () -> assertEquals("newTest", testCardList.getName()),
                () -> assertTrue(testCardList.getArchived()),
                () -> assertEquals(board.getId(), testCardList.getBoardId())
        );
    }
    @Test
    public void updateFailure() {
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setCreatedBy("selim");
        CardList testCardList = new CardList();
        UUID id = UUID.randomUUID();
        testCardList.setId(id);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.update(member, testCardList),
                "expected to throw Illegal state exception, but it didn't"
        );
        assertEquals("CardList with ID: "  + id +  " doesn't exists", ex.getMessage());
    }
}
