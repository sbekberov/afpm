package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.domain.Member;
import spd.trello.repository.CardListRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CardListService extends AbstractService<CardList>{

    CardListRepository cardListRepository;

    public CardListService(CardListRepository cardListRepository) {
        this.cardListRepository = cardListRepository;
    }
    public CardListService() {
        super();
        cardListRepository = new CardListRepository(dataSource);
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


    public CardList update(Member member, CardList cardList) throws IllegalAccessException {
        cardListRepository.update(cardList);
        return cardListRepository.findById(cardList.getId());
    }


    public List<CardList> getAll() {
        cardListRepository.getAll();
        return null;
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
