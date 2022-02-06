package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CheckList;
import spd.trello.repository.CRUDRepository;

@Service
public class CheckListService extends AbstractService<CheckList> {

    public CheckListService(CRUDRepository<CheckList> checklistRepository) {
        super(checklistRepository);
    }

}
