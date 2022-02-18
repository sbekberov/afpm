package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepository;


@Service
public class ReminderService extends AbstractService<Reminder, ReminderRepository> {
    @Autowired
    public ReminderService(ReminderRepository repository) {
        super(repository);
    }
}
