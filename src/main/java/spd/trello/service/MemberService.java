package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Member;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.MemberRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class MemberService extends AbstractService<Member, MemberRepository> {
    private final WorkspaceService workspaceService;
    private final CardService cardService;
    private final BoardService boardService;

    @Autowired
    public MemberService(MemberRepository repository, WorkspaceService workspaceService, BoardService boardService, CardService cardService) {
        super(repository);
        this.workspaceService = workspaceService;
        this.cardService = cardService;
        this.boardService = boardService;
    }

    @Override
    public Member update(Member entity) {
        Member oldMember = findById(entity.getId());
        entity.setUpdatedDate(LocalDateTime.now().withNano(0));
        entity.setCreatedBy(oldMember.getCreatedBy());
        entity.setCreatedDate(oldMember.getCreatedDate());
        entity.setUserId(oldMember.getUserId());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getRole().equals(oldMember.getRole())) {
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
        workspaceService.deleteMemberInWorkspaces(id);
        boardService.deleteMemberInBoards(id);
        cardService.deleteMemberInCards(id);
        super.delete(id);
    }

}
