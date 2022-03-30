package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.CardList;
import spd.trello.domain.CheckableItem;
import spd.trello.service.CardListService;
import spd.trello.service.CheckableItemService;


@RestController
@RequestMapping("/checkableItems")
public class CheckableItemController extends AbstractController<CheckableItem, CheckableItemService> {
    public CheckableItemController (CheckableItemService service) {
        super(service);
    }
}
