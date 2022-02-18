package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Attachment;
import spd.trello.domain.Board;
import spd.trello.repository.AttachmentRepository;
import spd.trello.repository.BoardRepository;


@Service
public class BoardService extends AbstractService<Board, BoardRepository> {
    @Autowired
    public BoardService(BoardRepository repository) {
        super(repository);
    }

}
