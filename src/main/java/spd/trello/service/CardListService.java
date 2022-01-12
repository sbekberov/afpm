package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.domain.Member;
import spd.trello.repository.CardListRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class CardListService extends AbstractService<CardList>{

    CardListRepository cardListRepository = new CardListRepository();

    public CardListService() throws SQLException, IOException {
    }


    public CardList create(Member member, UUID boardId, String name) throws IllegalAccessException {
        CardList cardList = new CardList();
        cardList.setId(UUID.randomUUID());
        cardList.setCreatedBy(member.getCreatedBy());
        cardList.setCreatedDate(Date.valueOf(LocalDate.now()));
        cardList.setName(name);
        cardList.setBoardId(boardId);
        cardListRepository.create(cardList);
        return cardListRepository.findById(cardList.getId());
    }


    public void update(CardList cardList) throws IllegalAccessException {
        cardListRepository.update(cardList);
    }


    public void getAll() {
        cardListRepository.getAll();
    }


    public CardList findById(UUID id) {
        CardList cardList = null;
        try {
            cardList = cardListRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return cardList;
    }


    public boolean delete(UUID id) {
        return cardListRepository.delete(id);
    }
}
