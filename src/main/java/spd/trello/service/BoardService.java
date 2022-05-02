package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.BoardRepository;
import spd.trello.validators.BoardValidator;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
public class BoardService extends AbstractService<Board, BoardRepository , BoardValidator> {

    private final CardListService cardListService;

    @Autowired
    public BoardService(BoardRepository repository, CardListService cardListService,BoardValidator boardValidator) {
        super(repository, boardValidator);
        this.cardListService = cardListService;
    }

    @Override
    public Board update(Board entity) {
        Board oldBoard = findById(entity.getId());
        entity.setUpdatedDate(LocalDateTime.now().withNano(0));
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

    @Override
    public void delete(UUID id) {
        cardListService.deleteCardListsForBoard(id);
        super.delete(id);
    }

    public void deleteBoardForWorkspace(UUID workspaceId) {
        repository.findAllByWorkspaceId(workspaceId).forEach(board -> delete(board.getId()));
    }

    public void deleteMemberInBoards(UUID memberId) {
        List<Board> boards = repository.findAllByMembersIdsEquals(memberId);
        for (Board board : boards) {
            Set<UUID> membersId = board.getMembersIds();
            membersId.remove(memberId);
            if (board.getMembersIds().isEmpty()) {
                delete(board.getId());
            }
        }
    }

}
