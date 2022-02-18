package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepository;

@Service
public class CardListService extends AbstractService<CardList, CardListRepository>{
    @Autowired
    public CardListService(CardListRepository repository) {
        super(repository);
    }
}
