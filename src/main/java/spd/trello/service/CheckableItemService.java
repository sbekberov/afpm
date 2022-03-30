package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CheckableItem;
import spd.trello.repository.CheckableItemRepository;


@Service
public class CheckableItemService extends AbstractService<CheckableItem, CheckableItemRepository> {
    @Autowired
    public CheckableItemService(CheckableItemRepository repository) {
        super(repository);
    }
}


