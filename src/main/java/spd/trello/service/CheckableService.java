package spd.trello.service;

import spd.trello.domain.CheckableItem;
import spd.trello.repository.CheckableItemRepository;

import java.util.UUID;

public class CheckableService extends  AbstractService<CheckableItem>{
    CheckableItemRepository checkAbleItemRepository;

    public CheckableService(CheckableItemRepository checkAbleItemRepository) {
        this.checkAbleItemRepository = checkAbleItemRepository;
    }

    public CheckableService() {
        super();
        checkAbleItemRepository = new   CheckableItemRepository(dataSource);
    }

    public CheckableItem create(String name, UUID checklistID) throws IllegalAccessException {
        CheckableItem checkableItem = new CheckableItem();
        checkableItem.setId(UUID.randomUUID());
        checkableItem.setName(name);
        checkableItem.setChecked(Boolean.FALSE);
        checkableItem.setChecklistId(checklistID);
        checkAbleItemRepository.create(checkableItem);
        return checkAbleItemRepository.findById(checkableItem.getId());
    }



    public void update(CheckableItem checkableItem) {
        checkAbleItemRepository.update(checkableItem);
    }

    public void getAll() {
        checkAbleItemRepository.getAll();
    }

    public CheckableItem findById(UUID id) {
        CheckableItem checkableItem = null;
        try {
            checkableItem = checkAbleItemRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return checkableItem;
    }

    public boolean delete(UUID id) {
        return checkAbleItemRepository.delete(id);
    }
}