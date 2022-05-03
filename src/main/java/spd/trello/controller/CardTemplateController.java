package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.CardTemplate;
import spd.trello.service.CardTemplateService;


@RestController
@RequestMapping("/cards-templates")
public class CardTemplateController extends AbstractController<CardTemplate, CardTemplateService> {
    public CardTemplateController (CardTemplateService service) {
        super(service);
    }
}
