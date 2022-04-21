package spd.trello.reminder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spd.trello.domain.Reminder;
import spd.trello.repository.ReminderRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TreeSet;

@Component
@EnableScheduling
public class ReminderScheduler {
    @Autowired
    private ReminderRepository repository;

    private TreeSet<Reminder> reminders = new TreeSet<>();

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    public void deleteReminder(Reminder reminder) {
        reminders.remove(reminder);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void runReminder() {
        while (reminders.size() != 0 &&
                (reminders.first().getRemindOn().isEqual(LocalDateTime.now().withNano(0)) ||
                        reminders.first().getRemindOn().isBefore(LocalDateTime.now().withNano(0)))) {
            Reminder reminder = reminders.pollFirst();
            Objects.requireNonNull(reminder).setActive(true);
            repository.save(reminder);
            System.out.println("Time to finish!" + Objects.requireNonNull(reminder).getId());
        }
    }

    @PostConstruct
    private void initListReminders() {
        reminders = repository.findAllByActive(true);
    }
}
