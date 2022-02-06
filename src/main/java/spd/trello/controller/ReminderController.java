package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Reminder;
import spd.trello.service.ReminderService;


@RestController
@RequestMapping("/reminders")
public class ReminderController extends AbstractController<Reminder, ReminderService> {
    public ReminderController (ReminderService service) {
        super(service);
    }
}
