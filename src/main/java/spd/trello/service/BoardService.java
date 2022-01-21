package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Member;
import spd.trello.domain.Role;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BoardService extends AbstractService<Board>{

    public BoardService (CRUDRepository<Board> boardRepository){
        super(boardRepository);
    }


    public Board create(Member member, UUID workspaceId, String name, String description)  {
        Board board = new Board();
        board.setId(UUID.randomUUID());
        board.setCreatedBy(member.getCreatedBy());
        board.setCreatedDate(Date.valueOf(LocalDate.now()));
        board.setName(name);
        if (description != null) {
            board.setDescription(description);
        }
        board.setWorkspaceId(workspaceId);
        repository.create(board);
        return repository.findById(board.getId());
    }


    public Board update(Member member , Board board) {
        if(member.getRole() == Role.GUEST){
            throw new IllegalStateException("This user cannot update workspace");
        }
        board.setUpdatedBy(member.getCreatedBy());
        board.setUpdatedDate(Date.valueOf(LocalDate.now()));
        repository.update(board);
        return repository.findById(board.getId());
    }


    public List<Board> getAll() {
        return repository.getAll();
    }


    public Board findById(UUID id) {
        return repository.findById(id);
    }


    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
