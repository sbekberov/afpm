package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.*;
import spd.trello.repository.CardRepository;
import spd.trello.service.CardService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spd.trello.Helper.*;

public class CardTest extends BaseTest{

    private final CardService service;

    public CardTest() {
        this.service = new CardService(new CardRepository(dataSource));
    }

    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card testCard = service.create(member, cardList.getId(), "test", "test");
        assertNotNull(testCard);
        assertAll(
                () -> assertEquals("test@gmail", testCard.getCreatedBy()),
                () -> assertNull(testCard.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testCard.getCreatedDate()),
                () -> assertNull(testCard.getUpdatedDate()),
                () -> assertEquals("test", testCard.getName()),
                () -> assertEquals("test", testCard.getDescription()),
                () -> assertFalse(testCard.getArchived()),
                () -> assertEquals(cardList.getId(), testCard.getCardListId())
        );
    }
    @Test
    public void testFindAll() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card testFirstCard = service.create(member, cardList.getId(), "firstCard", "firstDescription");
        Card testSecondCard = service.create(member, cardList.getId(), "secondCard", "secondDescription");
        assertNotNull(testFirstCard);
        assertNotNull(testSecondCard);
        List<Card> testCard = service.getAll();
        assertAll(
                () -> assertTrue(testCard.contains(testFirstCard)),
                () -> assertTrue(testCard.contains(testSecondCard))
        );
    }
    @Test
    public void createFailure() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> service.create(member, null, "test", "test"),
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
        assertEquals("Card with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void testDelete() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card testCard = service.create(member,cardList.getId(), "test", "description");
        assertNotNull(testCard);
        UUID id = testCard.getId();
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
        CardList cardList = getNewCardList(member, board.getId());
        Card card = service.create(member, cardList.getId(), "test", "description");
        assertNotNull(card);
        card.setName("newTest");
        card.setDescription("newDescription");
        card.setArchived(true);
        Card card1 = service.update(member, card);
        assertAll(
                () -> assertEquals("test@gmail", card1.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), card1.getUpdatedDate()),
                () -> assertEquals("newTest", card1.getName()),
                () -> assertEquals("newDescription", card1.getDescription()),
                () -> assertTrue(card1.getArchived()),
                () -> assertEquals(cardList.getId(), card1.getCardListId())
        );
    }

@Test
public void updateFailure() {
    Member member = new Member();
    member.setRole(Role.ADMIN);
    member.setCreatedBy("selim");
    Card testCard = new Card();
    testCard.setId(UUID.randomUUID());
    IllegalStateException ex = assertThrows(
            IllegalStateException.class,
            () -> service.update(member, testCard),
            "expected to throw Illegal state exception, but it didn't"
    );
    assertEquals("Card with ID: " + testCard.getId() + " doesn't exists", ex.getMessage());
}
}
