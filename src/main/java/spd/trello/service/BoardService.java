package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.BoardRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class BoardService extends AbstractService<Board, BoardRepository> {
    @Autowired
    public BoardService(BoardRepository repository) {
        super(repository);
    }

    @Override
    public Board update(Board entity) {
        Board oldBoard = findById(entity.getId());
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setCreatedBy(oldBoard.getCreatedBy());
        entity.setCreatedDate(oldBoard.getCreatedDate());
        entity.setWorkspaceId(oldBoard.getWorkspaceId());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getName() == null && entity.getDescription() == null
                && entity.getMembersIds().equals(oldBoard.getMembersIds())
                && entity.getVisibility().equals(oldBoard.getVisibility())) {
            throw new ResourceNotFoundException();
        }

        if (entity.getDescription() == null && oldBoard.getDescription() != null) {
            entity.setDescription(oldBoard.getDescription());
        }
        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
