package spd.trello.service;

import spd.trello.domain.Checklist;
import spd.trello.domain.Member;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CheckListService extends AbstractService<Checklist>{

    public CheckListService(CRUDRepository<Checklist> checklistRepository){
        super(checklistRepository);
    }


    public Checklist create(Member member, String name , UUID cardId)  {
        Checklist checklist = new Checklist();
        checklist.setId(UUID.randomUUID());
        checklist.setName(name);
        checklist.setCreatedBy(member.getCreatedBy());
        checklist.setCreatedDate(Date.valueOf(LocalDate.now()));
        checklist.setCardId(cardId);
        repository.create(checklist);
        return repository.findById(checklist.getId());
    }


    public Checklist update(Member member , Checklist entity) {
        Checklist oldChecklist = repository.findById(entity.getId());
        entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        if (entity.getName() == null) {
            entity.setName(oldChecklist.getName());
        }
        return repository.update(entity);
    }


    public List<Checklist> getAll() {
        return repository.getAll();
    }

    public Checklist findById(UUID id) {
        return repository.findById(id);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }

}
