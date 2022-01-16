package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Member;
import spd.trello.repository.BoardRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BoardService extends AbstractService<Board>{

    BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardService() {
        super();
        boardRepository = new BoardRepository(dataSource);
    }



    public Board create(Member member, UUID workspaceId, String name, String description) throws IllegalAccessException {
        Board board = new Board();
        board.setId(UUID.randomUUID());
        board.setCreatedBy(member.getCreatedBy());
        board.setCreatedDate(Date.valueOf(LocalDate.now()));
        board.setName(name);
        if (description != null) {
            board.setDescription(description);
        }
        board.setWorkspaceId(workspaceId);
        boardRepository.create(board);
        return boardRepository.findById(board.getId());
    }


    public Board update(Board board) throws IllegalAccessException {
        boardRepository.update(board);
        return boardRepository.findById(board.getId());
    }


    public List<Board> getAll() {
        boardRepository.getAll();
        return null;
    }


    public Board findById(UUID id) {
        Board board = null;
        try {
            board = boardRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return board;
    }


    public boolean delete(UUID id) {
        return boardRepository.delete(id);
    }
}
