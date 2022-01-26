package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.repository.CRUDRepository;

@Service
public class BoardService extends AbstractService<Board> {

    public BoardService(CRUDRepository<Board> boardRepository) {
        super(boardRepository);
    }

}
