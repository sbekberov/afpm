package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Card;
import spd.trello.repository.CardRepository;

@Service
public class CardService extends AbstractService<Card, CardRepository> {
    @Autowired
    public CardService(CardRepository repository) {
        super(repository);
    }
}
