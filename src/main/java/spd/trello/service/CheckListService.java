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
import java.util.UUID;

@Service
public class CheckListService extends AbstractService<CheckList, CheckListRepository> {
    private final CheckableItemService checkableItemService;

    @Autowired
    public CheckListService(CheckListRepository repository, CheckableItemService checkableItemService) {
        super(repository);
        this.checkableItemService = checkableItemService;
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

    @Override
    public void delete(UUID id) {
        checkableItemService.deleteCheckableItemsForCheckList(id);
        super.delete(id);
    }

    public void deleteCheckListsForCard(UUID cardId) {
        repository.findAllByCardId(cardId).forEach(checklist -> delete(checklist.getId()));
    }
}
