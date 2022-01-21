package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.*;
import spd.trello.repository.CommentRepository;
import spd.trello.service.CommentService;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spd.trello.Helper.*;
import static spd.trello.Helper.getNewCard;

public class CommentTest extends BaseTest{
    private final CommentService service;

    public CommentTest(){
        this.service = new CommentService(new CommentRepository(dataSource));
    }
    @Test
    public void successCreate() {
        User users = getNewUser("test@gmail");
        Member member = getNewMember(users);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Comment testComment = service.create(member,"Test", card.getId(), users.getId());
        assertNotNull(testComment);
        assertAll(
                () -> assertEquals("test@gmail", testComment.getCreatedBy())
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
        Comment testFirstComment = service.create(member, "test",card.getId(), user.getId());
        Comment testSecondComment = service.create(member, "content" ,card.getId(), user.getId());
        assertNotNull(testFirstComment);
        assertNotNull(testSecondComment);
        List<Comment> testComments = service.getAll();
        assertAll(
                () -> assertTrue(testComments.contains(testFirstComment)),
                () -> assertTrue(testComments.contains(testSecondComment))
        );
    }
    @Test
    public void findById() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(id),
                "no exception"
        );
        assertEquals("Comment with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void delete() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace workspace = getNewWorkspace(member);
        Board board = getNewBoard(member, workspace.getId());
        CardList cardList = getNewCardList(member, board.getId());
        Card card = getNewCard(member, cardList.getId());
        Comment testComment = service.create(member, "test", card.getId(), user.getId());
        assertNotNull(testComment);
        UUID id = testComment.getId();
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
        Card card = getNewCard(member,cardList.getId());
        Comment comment = service.create(member, "test", card.getId(), user.getId());
        assertNotNull(comment);
        comment.setContent("testtest");
        Comment testComment = service.update(member, comment);
        assertAll(
                () -> assertEquals("test@gmail", testComment.getCreatedBy()),
                () -> assertEquals("test@gmail", testComment.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testComment.getCreatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testComment.getUpdatedDate()),
                () -> assertEquals("testtest", testComment.getContent())
        );
    }
    @Test
    public void updateFailure() {
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setCreatedBy("selim");
        Comment testComment = new Comment();
        UUID id = UUID.randomUUID();
        testComment.setId(id);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.update(member, testComment),
                "expected to throw Illegal state exception, but it didn't"
        );
        assertEquals("Comment with ID: " + id + " doesn't exists", ex.getMessage());
    }
}
