package spd.trello.integrationalTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.*;
import spd.trello.repository.*;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class Helper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CardListRepository cardListRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BoardTemplateRepository boardTemplateRepository;
    @Autowired
    private CardTemplateRepository cardTemplateRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CheckListRepository checkListRepository;
    @Autowired
    private CheckableItemRepository checkableItemRepository;

    public User getNewUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName("Selim");
        user.setLastName("Bekberov");
        user.setCreatedDate(LocalDateTime.now());
        user.setCreatedBy(email);
        return userRepository.save(user);
    }

    public Member getNewMember(String email) {
        User user = getNewUser(email);
        Member member = new Member();
        user.setCreatedDate(LocalDateTime.now());
        member.setCreatedBy(user.getEmail());
        member.setUserId(user.getId());
        return memberRepository.save(member);
    }

    public List<Member> getMembersArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public Workspace getNewWorkspace(String email) {
        Member member = getNewMember(email);
        Workspace workspace = new Workspace();
        workspace.setCreatedBy(member.getCreatedBy());
        workspace.setCreatedDate(LocalDateTime.now());
        workspace.setName("Workspace");
        Set<UUID> membersId = new HashSet<>();
        membersId.add(member.getId());
        workspace.setMembersIds(membersId);
        return workspaceRepository.save(workspace);
    }

    public List<Workspace> getWorkspacesArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public Board getNewBoard(String email) {
        Workspace workspace = getNewWorkspace(email);
        Board board = new Board();
        board.setCreatedBy(workspace.getCreatedBy());
        board.setCreatedDate(LocalDateTime.now());
        board.setName("Board");
        board.setWorkspaceId(workspace.getId());
        board.setMembersIds(workspace.getMembersIds());
        return boardRepository.save(board);
    }

    public List<Board> getBoardsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public CardList getNewCardList(String email) {
        Board board = getNewBoard(email);
        CardList cardList = new CardList();
        cardList.setBoardId(board.getId());
        cardList.setCreatedBy(board.getCreatedBy());
        cardList.setCreatedDate(LocalDateTime.now());
        cardList.setName("CardList");
        return cardListRepository.save(cardList);
    }

    public List<CardList> getCardListsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public Card getNewCard(String email) {
        CardList cardList = getNewCardList(email);

        Reminder reminder = new Reminder();
        reminder.setRemindOn(LocalDateTime.now());
        reminder.setStart(LocalDateTime.now());
        reminder.setFinish(LocalDateTime.now());

        Card card = new Card();
        card.setCreatedBy(cardList.getCreatedBy());
        card.setCreatedDate(LocalDateTime.now());
        card.setCardListId(cardList.getId());
        card.setName("name");
        card.setReminder(reminder);
        card.setArchived(false);
        return cardRepository.save(card);
    }

    public List<Card> getCardsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public BoardTemplate getNewBoardTemplate(String name , String description) {
        BoardTemplate boardTemplate = new BoardTemplate();
        boardTemplate.setName("name");
        boardTemplate.setDescription("description");
        boardTemplate.setCreatedDate(LocalDateTime.now());
        boardTemplate.setCreatedBy("ssss");
        return boardTemplateRepository.save(boardTemplate);
    }

    public List<BoardTemplate> getBoardTemplatesArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public CardTemplate getNewCardTemplate(String name , String description) {
        CardTemplate cardTemplate = new CardTemplate();
        cardTemplate.setName("name");
        cardTemplate.setDescription("description");
        cardTemplate.setCreatedDate(LocalDateTime.now());
        cardTemplate.setCreatedBy("ssss");
        return cardTemplateRepository.save(cardTemplate);
    }

    public List<CardTemplate> getCardTemplatesArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public Attachment getNewAttachment(String email) {
        Card card = getNewCard(email);
        Attachment attachment = new Attachment();
        attachment.setCardId(card.getId());
        attachment.setCreatedBy(card.getCreatedBy());
        attachment.setCreatedDate(card.getCreatedDate());
        attachment.setName("name");
        attachment.setLink("link");
        return attachmentRepository.save(attachment);
    }

    public List<Attachment> getAttachmentsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public Comment getNewComment(String email) {
        Card card = getNewCard(email);
        Comment comment = new Comment();
        comment.setCardId(card.getId());
        comment.setCreatedBy(card.getCreatedBy());
        comment.setCreatedDate(card.getCreatedDate());
        comment.setText("text");
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public CheckList getNewСheckList(String email) {
        Card card = getNewCard(email);
        CheckList checkList = new CheckList();
        checkList.setName("name");
        checkList.setCardId(card.getId());
        checkList.setCreatedBy(card.getCreatedBy());
        checkList.setCreatedDate(card.getCreatedDate());
        return checkListRepository.save(checkList);
    }

    public List<CheckList> getCheckListArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    public CheckableItem getNewCheckableItem(String email) {
        CheckList checklist = getNewСheckList(email);
        CheckableItem checkableItem = new CheckableItem();
        checkableItem.setChecklistId(checklist.getId());
        checkableItem.setName("name");
        return checkableItemRepository.save(checkableItem);
    }

    public List<CheckableItem> getCheckableItemsArray(MvcResult mvcResult)
            throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }




    public Set<UUID> getIdsFromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, new TypeReference<>() {
        });
    }
}
