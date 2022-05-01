package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.reminder.ReminderScheduler;
import spd.trello.domain.Card;
import spd.trello.exception.BadRequestException;
import spd.trello.repository.CardRepository;
import spd.trello.validators.CardValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CardService extends AbstractService<Card, CardRepository, CardValidator> {

    public final ReminderScheduler reminderScheduler;
    private final CheckListService checkListService;
    private final LabelService labelService;
    private final CommentService commentService;
    private final AttachmentService attachmentService;

    @Autowired
    public CardService(CardRepository repository, ReminderScheduler reminderScheduler, CheckListService checkListService, AttachmentService attachmentService, CommentService commentService, LabelService labelService,CardValidator cardValidator) {
        super(repository,cardValidator);
        this.reminderScheduler = reminderScheduler;
        this.checkListService = checkListService;
        this.commentService = commentService;
        this.attachmentService = attachmentService;
        this.labelService = labelService;
    }


    @Override
    public void delete(UUID id) {
        checkListService.deleteCheckListsForCard(id);
        labelService.deleteLabelsForCard(id);
        commentService.deleteCommentsForCard(id);
        attachmentService.deleteAttachmentsForCard(id);
        super.delete(id);
    }

    public void deleteMemberInCards(UUID memberId) {
        List<Card> cards = repository.findAllByMembersIdsEquals(memberId);
        for (Card card : cards) {
            Set<UUID> membersId = card.getMembersIds();
            membersId.remove(memberId);
            if (card.getMembersIds().isEmpty()) {
                delete(card.getId());
            }
        }
    }

    public void deleteCardsForCardList(UUID cardListId) {
        repository.findAllByCardListId(cardListId).forEach(card -> delete(card.getId()));
    }

}
