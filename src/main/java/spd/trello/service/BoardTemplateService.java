package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.BoardTemplate;
import spd.trello.repository.BoardTemplateRepository;

@Service
public class BoardTemplateService extends AbstractService<BoardTemplate, BoardTemplateRepository> {
    @Autowired
    public BoardTemplateService(BoardTemplateRepository repository) {
        super(repository);
    }

}