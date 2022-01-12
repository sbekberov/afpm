package spd.trello.service;

import spd.trello.domain.Checklist;
import spd.trello.repository.CheckListRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class CheckListService extends AbstractService<Checklist>{

    CheckListRepository checkListRepository = new CheckListRepository();

    public CheckListService() throws SQLException, IOException {
    }


    public Checklist create(String name , UUID cardId) throws IllegalAccessException {
        Checklist checklist = new Checklist();
        checklist.setId(UUID.randomUUID());
        checklist.setName(name);
        checklist.setCreatedBy("test");
        checklist.setCreatedDate(Date.valueOf(LocalDate.now()));
        checklist.setCardId(cardId);
        checkListRepository.create(checklist);
        return checkListRepository.findById(checklist.getId());
    }


    public void update(Checklist checklist) {
        checkListRepository.update(checklist);
    }


    public void getAll() {
        checkListRepository.getAll();
    }

    public Checklist findById(UUID id) {
        Checklist checklist = null;
        try {
            checklist = checkListRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return checklist;
    }

    public boolean delete(UUID id) {
        return checkListRepository.delete(id);
    }

}
