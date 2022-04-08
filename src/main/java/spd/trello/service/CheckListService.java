package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CheckList;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.CheckListRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CheckListService extends AbstractService<CheckList, CheckListRepository> {
    @Autowired
    public CheckListService(CheckListRepository repository) {
        super(repository);
    }

    @Override
    public CheckList update(CheckList entity) {
        CheckList oldChecklist = findById(entity.getId());
        entity.setCreatedBy(oldChecklist.getCreatedBy());
        entity.setCreatedDate(oldChecklist.getCreatedDate());
        entity.setUpdatedDate(LocalDateTime.now().withNano(0));
        entity.setCardId(oldChecklist.getCardId());


        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getName() == null) {
            throw new ResourceNotFoundException();
        }

        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
