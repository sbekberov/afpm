package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.domain.Member;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CardService extends AbstractService<Card> {

    public CardService(CRUDRepository<Card> cardRepository){
        super(cardRepository);
    }


    public Card create(Member member, UUID cardListId, String name, String description){
        Card card = new Card();
        card.setName(name);
        card.setDescription(description);
        card.setId(UUID.randomUUID());
        card.setCreatedBy(member.getCreatedBy());
        card.setCreatedDate(Date.valueOf(LocalDate.now()));
        card.setCardListId(cardListId);
        repository.create(card);
        return findById(card.getId());
    }


    public Card update(Member member ,Card card){
        Card oldCard = repository.findById(card.getId());
        card.setUpdatedBy(member.getCreatedBy());
        card.setUpdatedDate(Date.valueOf(LocalDate.now()));
        if (card.getName() == null) {
            card.setName(oldCard.getName());
        }
        if (card.getDescription() == null && oldCard.getDescription() != null) {
            card.setDescription(oldCard.getDescription());
        }
        if (card.getArchived() == null) {
            card.setArchived(oldCard.getArchived());
        }
        return repository.update(card);
    }


    public List<Card> getAll() {
        return repository.getAll();
    }


    public Card findById(UUID id) {
        return repository.findById(id);
    }


    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
