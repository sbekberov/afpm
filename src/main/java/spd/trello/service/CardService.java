package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.domain.Member;
import spd.trello.repository.CardRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class CardService extends AbstractService<Card> {

    CardRepository cardRepository = new CardRepository();

    public CardService() throws SQLException, IOException {
    }


    public Card create(String name, UUID cardListId, String description, Member member) throws IllegalAccessException {
        Card card = new Card();
        card.setName(name);
        card.setDescription(description);
        card.setId(UUID.randomUUID());
        card.setCreatedBy("test");
        card.setCreatedDate(Date.valueOf(LocalDate.now()));
        card.setArchived(Boolean.FALSE);
        card.setCardListId(cardListId);
        cardRepository.create(card);
        return cardRepository.findById(card.getId());
    }


    public void update(Card card) {
        cardRepository.update(card);
    }


    public void getAll() {
        cardRepository.getAll();
    }


    public Card findById(UUID id) {
        Card card = null;
        try {
            card = cardRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return card;
    }


    public boolean delete(UUID id) {
        return cardRepository.delete(id);
    }
}
