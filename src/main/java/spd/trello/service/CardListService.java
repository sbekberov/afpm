package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.repository.CRUDRepository;

@Service
public class CardListService extends AbstractService<CardList> {

    public CardListService(CRUDRepository<CardList> cardListRepository) {
        super(cardListRepository);
    }


}
