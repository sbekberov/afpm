package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.*;
import spd.trello.repository.ReminderRepository;
import spd.trello.service.ReminderService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spd.trello.Helper.*;
import static spd.trello.Helper.getNewCard;

public class ReminderTest extends BaseTest{
    private final ReminderService service;

    public ReminderTest(){
        this.service = new ReminderService(new ReminderRepository(dataSource));
    }
    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Reminder testReminder = service.create(
                member,
                card.getId(),
                Date.valueOf(LocalDate.of(2022, 4, 26)),
                Date.valueOf(LocalDate.of(2022, 4, 26))
        );
        assertNotNull(testReminder);
        assertAll(
                () -> assertEquals("test@gmail", testReminder.getCreatedBy()),
                () -> assertNull(testReminder.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testReminder.getCreatedDate()),
                () -> assertNull(testReminder.getUpdatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testReminder.getStart()),
                () -> assertEquals(Date.valueOf(LocalDate.of(2022, 4, 26)), testReminder.getEnd()),
                () -> assertEquals(Date.valueOf(LocalDate.of(2022, 4, 26)), testReminder.getRemindOn()),
                () -> assertFalse(testReminder.getActive()),
                () -> assertEquals(card.getId(), testReminder.getCardId())
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
        Reminder testFirstReminder = service.create(
                member,
                card.getId(),
                Date.valueOf(LocalDate.of(2022, 4, 26)),
                Date.valueOf(LocalDate.of(2022, 4, 26))
        );
        Reminder testSecondReminder = service.create(
                member,
                card.getId(),
                Date.valueOf(LocalDate.of(2022, 4, 22)),
                Date.valueOf(LocalDate.of(2022, 4, 22))
        );
        assertNotNull(testFirstReminder);
        assertNotNull(testSecondReminder);
        List<Reminder> testComments = service.getAll();
        assertAll(
                () -> assertTrue(testComments.contains(testFirstReminder)),
                () -> assertTrue(testComments.contains(testSecondReminder))
        );
    }
    @Test
    public void createFailure() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.create(member,
                        null,
                        Date.valueOf(LocalDate.of(2022, 4, 26)),
                        Date.valueOf(LocalDate.of(2022, 4, 26))),
                "expected to throw  IllegalStateException, but it didn't"
        );
        assertEquals("Error ReminderRepository create", ex.getMessage());
    }
    @Test
    public void findByIdFailure() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(id),
                "no exception"
        );
        assertEquals("Reminder with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void delete() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Reminder testReminder = service.create(
                member,
                card.getId(),
                Date.valueOf(LocalDate.of(2022, 4, 26)),
                Date.valueOf(LocalDate.of(2022, 4, 26))
        );
        assertNotNull(testReminder);
        UUID id = testReminder.getId();
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
        Reminder reminder = service.create(member,
                card.getId(),
                Date.valueOf(LocalDate.of(2022, 4, 26)),
                Date.valueOf(LocalDate.of(2022, 4, 26)));
        assertNotNull(reminder);
        reminder.setEnd(Date.valueOf(LocalDate.of(2022, 4, 25)));
        reminder.setRemindOn(Date.valueOf(LocalDate.of(2022, 4, 25)));
        reminder.setActive(false);
        Reminder testReminder = service.update(member, reminder);
        assertAll(
                () -> assertEquals("test@gmail", testReminder.getCreatedBy()),
                () -> assertEquals("test@gmail", testReminder.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testReminder.getCreatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testReminder.getUpdatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testReminder.getStart()),
                () -> assertEquals(Date.valueOf(LocalDate.of(2022, 4, 25)), testReminder.getEnd()),
                () -> assertEquals(Date.valueOf(LocalDate.of(2022, 4, 25)), testReminder.getRemindOn()),
                () -> assertFalse(testReminder.getActive())
        );
    }


}
