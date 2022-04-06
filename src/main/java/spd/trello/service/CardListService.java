package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.CardListRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CardListService extends AbstractService<CardList, CardListRepository> {
    @Autowired
    public CardListService(CardListRepository repository) {
        super(repository);
    }

    @Override
    public CardList update(CardList entity) {
        CardList oldCardList = findById(entity.getId());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getName() == null && entity.getArchived() == oldCardList.getArchived()) {
            throw new ResourceNotFoundException();
        }

        entity.setUpdatedDate(LocalDateTime.now());
        entity.setCreatedBy(oldCardList.getCreatedBy());
        entity.setCreatedDate(oldCardList.getCreatedDate());
        entity.setBoardId(oldCardList.getBoardId());
        if (entity.getName() == null) {
            entity.setName(oldCardList.getName());
        }
        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}