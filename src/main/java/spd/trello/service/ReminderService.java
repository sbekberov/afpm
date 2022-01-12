package spd.trello.service;

import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReminderService extends AbstractService<Reminder>{

    ReminderRepository reminderRepository = new ReminderRepository();

    public ReminderService() throws SQLException, IOException {
    }


    public Reminder create(UUID cardId) throws IllegalAccessException {
        Reminder reminder = new Reminder();
        reminder.setId(UUID.randomUUID());
        reminder.setStart(LocalDateTime.now());
        reminder.setEnd(LocalDateTime.of(2025, 4, 4, 12, 1));
        reminder.setRemindOn(LocalDateTime.of(2023, 4, 4, 12, 1));
        reminder.setActive(Boolean.TRUE);
        reminder.setCreatedBy("test");
        reminder.setCreatedDate(Date.valueOf(LocalDate.now()));
        reminder.setCardId(cardId);
        reminderRepository.create(reminder);
        return reminderRepository.findById(reminder.getId());
    }


    public void update(Reminder reminder) throws IllegalAccessException {
        reminderRepository.update(reminder);
    }


    public void getAll() {
        reminderRepository.getAll();
    }


    public Reminder findById(UUID id) {
        Reminder reminder = null;
        try {
            reminder = reminderRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return reminder;
    }


    public boolean delete(UUID id) {
        return reminderRepository.delete(id);
    }
}
