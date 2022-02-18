package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CheckList;
import spd.trello.repository.CheckListRepository;

@Service
public class CheckListService extends AbstractService<CheckList, CheckListRepository>{
    @Autowired
    public CheckListService(CheckListRepository repository) {
        super(repository);
    }
}
