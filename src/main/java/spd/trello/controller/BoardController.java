package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Board;
import spd.trello.service.BoardService;


@RestController
@RequestMapping("/boards")
public class BoardController extends AbstractController<Board, BoardService> {
    public BoardController (BoardService service) {
        super(service);
    }
}
