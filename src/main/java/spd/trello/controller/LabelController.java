package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Label;
import spd.trello.service.LabelService;

@RestController
@RequestMapping("/labels")
public class LabelController extends AbstractController<Label, LabelService> {
    public LabelController (LabelService service) {
        super(service);
    }
}
