package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Reminder;
import spd.trello.repository.CRUDRepository;

@Service
public class ReminderService extends AbstractService<Reminder> {

    public ReminderService(CRUDRepository<Reminder> reminderRepository) {
        super(reminderRepository);
    }


}
