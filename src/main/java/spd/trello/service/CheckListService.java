package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Checklist;
import spd.trello.repository.CRUDRepository;

@Service
public class CheckListService extends AbstractService<Checklist> {

    public CheckListService(CRUDRepository<Checklist> checklistRepository) {
        super(checklistRepository);
    }

}
