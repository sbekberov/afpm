package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.reminder.ReminderScheduler;
import spd.trello.domain.Card;
import spd.trello.exception.BadRequestException;
import spd.trello.repository.CardRepository;

import java.time.LocalDateTime;

@Service
public class CardService extends AbstractService<Card, CardRepository> {
    public final ReminderScheduler reminderScheduler;
    @Autowired
    public CardService(CardRepository repository, ReminderScheduler reminderScheduler) {
        super(repository);
        this.reminderScheduler = reminderScheduler;
    }

    @Override
    public Card create(Card entity) {
        entity.setCreatedDate(LocalDateTime.now());

        try {
            Card card = repository.save(entity);
            if(card.getReminder().getActive()){
                reminderScheduler.addReminder(entity.getReminder());
            }
            return card;
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Card update(Card entity) {
        Card oldCard = findById(entity.getId());
        entity.setCardListId(oldCard.getCardListId());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setCreatedBy(oldCard.getCreatedBy());
        entity.setCreatedDate(oldCard.getCreatedDate());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getDescription() == null && oldCard.getDescription() != null) {
            entity.setDescription(oldCard.getDescription());
        }

        if(oldCard.getReminder().getActive() && !entity.getReminder().getActive()){
            reminderScheduler.deleteReminder(oldCard.getReminder());
        }
        try {
            Card card = repository.save(entity);
            if (card.getReminder().getActive() && !oldCard.getReminder().equals(card.getReminder()))
            {
                reminderScheduler.addReminder(card.getReminder());
            }
            return entity;
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
