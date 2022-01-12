package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Member;
import spd.trello.repository.BoardRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class BoardService extends AbstractService<Board>{

    BoardRepository boardRepository = new BoardRepository();

    public BoardService() throws SQLException, IOException {
    }


    public Board create(Member member, UUID workspaceId, String name, String description) throws IllegalAccessException {
        Board board = new Board();
        board.setId(UUID.randomUUID());
        board.setCreatedBy(member.getCreatedBy());
        board.setCreatedDate(Date.valueOf(LocalDate.now()));;
        board.setName(name);
        board.getMembers().add(member);
        if (description != null) {
            board.setDescription(description);
        }
        board.setWorkspaceId(workspaceId);
        boardRepository.create(board);
        return boardRepository.findById(board.getId());
    }


    public void update(Board board) throws IllegalAccessException {
        boardRepository.update(board);
    }


    public void getAll() {
        boardRepository.getAll();
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
