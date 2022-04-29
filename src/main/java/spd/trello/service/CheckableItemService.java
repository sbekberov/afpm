package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CheckableItem;
import spd.trello.repository.CheckableItemRepository;

import java.util.UUID;


@Service
public class CheckableItemService extends AbstractService<CheckableItem, CheckableItemRepository> {
    @Autowired
    public CheckableItemService(CheckableItemRepository repository) {
        super(repository);
    }

    public void deleteCheckableItemsForCheckList(UUID checkListId) {
        repository.findAllByChecklistId(checkListId).forEach(checkableItem -> delete(checkableItem.getId()));
    }
}


