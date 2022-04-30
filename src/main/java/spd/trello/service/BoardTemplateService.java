package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.BoardTemplate;
import spd.trello.domain.Label;
import spd.trello.repository.BoardTemplateRepository;
import spd.trello.repository.LabelRepository;
import spd.trello.validators.BoardTemplateValidator;
import spd.trello.validators.LabelValidator;

@Service
public class BoardTemplateService extends AbstractService<BoardTemplate, BoardTemplateRepository, BoardTemplateValidator> {
    @Autowired
    public BoardTemplateService(BoardTemplateRepository repository,BoardTemplateValidator boardTemplateValidator) {
        super(repository,boardTemplateValidator);
    }

}