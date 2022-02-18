package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CardTemplate;
import spd.trello.repository.CardTemplateRepository;

@Service
public class CardTemplateService extends AbstractService<CardTemplate, CardTemplateRepository> {
    @Autowired
    public CardTemplateService(CardTemplateRepository repository) {
        super(repository);
    }

}