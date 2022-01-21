package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.domain.Reminder;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReminderService extends AbstractService<Reminder>{

    public ReminderService(CRUDRepository<Reminder> reminderRepository){
        super(reminderRepository);
    }


    public Reminder create(Member member, UUID cardId, Date end, Date remindOn){
        Reminder reminder = new Reminder();
        reminder.setId(UUID.randomUUID());
        reminder.setStart(Date.valueOf(LocalDate.now()));
        reminder.setEnd(end);
        reminder.setRemindOn(remindOn);
        reminder.setCreatedBy(member.getCreatedBy());
        reminder.setCreatedDate(Date.valueOf(LocalDate.now()));
        reminder.setCardId(cardId);
        repository.create(reminder);
        return repository.findById(reminder.getId());
    }


    public Reminder update(Member member ,Reminder reminder){
        Reminder oldReminder = repository.findById(reminder.getId());
        reminder.setUpdatedBy(member.getCreatedBy());
        reminder.setUpdatedDate(Date.valueOf(LocalDate.now()));
        return repository.update(reminder);
    }


    public List<Reminder> getAll() {
        return repository.getAll();
    }


    public Reminder findById(UUID id) {
        return repository.findById(id);
    }


    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
