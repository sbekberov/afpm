package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.BoardTemplate;
import spd.trello.repository.BoardTemplateRepository;
import spd.trello.validators.BoardTemplateValidator;

@Service
public class BoardTemplateService extends AbstractService<BoardTemplate, BoardTemplateRepository, BoardTemplateValidator> {
    @Autowired
    public BoardTemplateService(BoardTemplateRepository repository,BoardTemplateValidator boardTemplateValidator) {
        super(repository,boardTemplateValidator);
    }

}