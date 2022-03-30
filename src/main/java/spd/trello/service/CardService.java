package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Card;
import spd.trello.exception.BadRequestException;
import spd.trello.repository.CardRepository;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class CardService extends AbstractService<Card, CardRepository> {
    @Autowired
    public CardService(CardRepository repository) {
        super(repository);
    }

    @Override
    public Card update(Card entity) {
        Card oldCard = findById(entity.getId());
        entity.setCardListId(oldCard.getCardListId());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        entity.setCreatedBy(oldCard.getCreatedBy());
        entity.setCreatedDate(oldCard.getCreatedDate());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getDescription() == null && oldCard.getDescription() != null) {
            entity.setDescription(oldCard.getDescription());
        }
        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}