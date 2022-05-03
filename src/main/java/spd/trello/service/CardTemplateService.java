package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CardTemplate;
import spd.trello.repository.CardTemplateRepository;
import spd.trello.validators.CardTemplateValidator;

@Service
public class CardTemplateService extends AbstractService<CardTemplate, CardTemplateRepository, CardTemplateValidator> {
    @Autowired
    public CardTemplateService(CardTemplateRepository repository, CardTemplateValidator cardTemplateValidator) {
        super(repository,cardTemplateValidator);
    }

}