package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.BoardTemplate;
import spd.trello.service.BoardTemplateService;


@RestController
@RequestMapping("/boardsTemplates")
public class BoardTemplateController extends AbstractController<BoardTemplate, BoardTemplateService> {
    public BoardTemplateController (BoardTemplateService service) {
        super(service);
    }
}
