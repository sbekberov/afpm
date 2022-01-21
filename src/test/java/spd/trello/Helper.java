package spd.trello;

import spd.trello.domain.*;
import spd.trello.repository.*;
import spd.trello.service.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static spd.trello.BaseTest.dataSource;

public class Helper {
    private static final UserService userService = new UserService(new UserRepository(dataSource));
    private static final MemberService memberService = new MemberService(new MemberRepository(dataSource));
    private static final WorkspaceService workspaceService = new WorkspaceService(new WorkspaceRepository(dataSource));
    private static final BoardService boardService = new BoardService(new BoardRepository(dataSource));
    private static final CardListService cardListService = new CardListService(new CardListRepository(dataSource));
    private static final CardService cardService = new CardService(new CardRepository(dataSource));
    private static final CommentService commentService = new CommentService(new CommentRepository(dataSource));
    private static final ReminderService reminderService = new ReminderService(new ReminderRepository(dataSource));

    public static User getNewUser(String email) {
        return userService.create("testFirstName", "testLastName", email);
    }

    public static Member getNewMember(User user) {
        return memberService.create(user, Role.ADMIN);
    }

    public static Workspace getNewWorkspace(Member member)  {
        return workspaceService.create(member, "MemberName", "description");
    }

    public static Board getNewBoard(Member member, UUID workspaceId)  {
        return boardService.create(member, workspaceId,"BoardName", "description");
    }

    public static CardList getNewCardList(Member member, UUID boardId) {
        return cardListService.create(member, boardId, "CardListName");
    }

    public static Card getNewCard(Member member, UUID cardListId){
        return cardService.create(member, cardListId,"cardName", "description");
    }

    public static Comment getNewComment(Member member,String content,UUID cardId, UUID userId) {
        return commentService.create(member , "text" ,cardId, userId);
    }

    public static Reminder getNewReminder(Member member, UUID cardId) throws IllegalAccessException {
        return reminderService.create(
                member,
                cardId,
                Date.valueOf(LocalDate.of(2022, 4, 26)),
                Date.valueOf(LocalDate.of(2022, 4, 26))
        );
    }
}