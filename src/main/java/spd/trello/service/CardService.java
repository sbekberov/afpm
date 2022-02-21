package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Card;
import spd.trello.repository.CRUDRepository;
@Service
public class CardService extends AbstractService<Card> {

    public CardService(CRUDRepository<Card> cardRepository){
        super(cardRepository);
    }
}
