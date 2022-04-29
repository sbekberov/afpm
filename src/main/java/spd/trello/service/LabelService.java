package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Label;
import spd.trello.repository.LabelRepository;
import spd.trello.validators.LabelValidator;

import java.util.UUID;

@Service
public class LabelService extends AbstractService<Label, LabelRepository, LabelValidator> {
    @Autowired
    public LabelService(LabelRepository repository,LabelValidator labelValidator) {
        super(repository,labelValidator);
    }

    public void deleteLabelsForCard(UUID cardId) {
        repository.findAllByCardId(cardId).forEach(label -> delete(label.getId()));
    }
}

