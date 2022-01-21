/*package spd.trello.service;

import spd.trello.domain.CheckableItem;
import spd.trello.repository.CRUDRepository;

import java.util.List;
import java.util.UUID;

public class CheckableService extends  AbstractService<CheckableItem>{

    public CheckableService(CRUDRepository<CheckableItem> checkableItemRepository){
        super(checkableItemRepository);
    }

    public CheckableItem create(String name, UUID checklistID) {
        CheckableItem checkableItem = new CheckableItem();
        checkableItem.setId(UUID.randomUUID());
        checkableItem.setName(name);
        checkableItem.setChecked(Boolean.FALSE);
        checkableItem.setChecklistId(checklistID);
        repository.create(checkableItem);
        return repository.findById(checkableItem.getId());
    }



    public  update(CheckableItem checkableItem) {
        repository.update(checkableItem);
    }

    public List<CheckableItem> getAll() {
        return repository.getAll();
    }

    public CheckableItem findById(UUID id) {
        return repository.findById(id);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}*/