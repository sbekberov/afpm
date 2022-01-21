package spd.trello.service;

import spd.trello.domain.CardList;
import spd.trello.domain.Member;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CardListService extends AbstractService<CardList>{

    public CardListService (CRUDRepository<CardList> cardListRepository){
        super(cardListRepository);
    }


    public CardList create(Member member, UUID boardId, String name)  {
        CardList cardList = new CardList();
        cardList.setId(UUID.randomUUID());
        cardList.setCreatedBy(member.getCreatedBy());
        cardList.setCreatedDate(Date.valueOf(LocalDate.now()));
        cardList.setName(name);
        cardList.setBoardId(boardId);
        repository.create(cardList);
        return repository.findById(cardList.getId());
    }


    public CardList update(Member member, CardList cardList){
        CardList oldCardList = findById(cardList.getId());
        cardList.setUpdatedBy(member.getCreatedBy());
        cardList.setUpdatedDate(Date.valueOf(LocalDate.now()));
        if (cardList.getName() == null) {
            cardList.setName(oldCardList.getName());
        }
        return repository.update(cardList);
    }


    public List<CardList> getAll() {
       return repository.getAll();
    }


    public CardList findById(UUID id) {
        return repository.findById(id);
    }


    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
