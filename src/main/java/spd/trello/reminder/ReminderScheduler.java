package spd.trello.reminder;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import spd.trello.domain.Reminder;
import spd.trello.repository.CardRepository;
import spd.trello.repository.ReminderRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@EnableScheduling
public class ReminderScheduler {

    private ReminderRepository repository;
    private final EmailSendler emailSendler;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final CardRepository cardRepository;

    public ReminderScheduler(ReminderRepository repository, EmailSendler emailSendler, CardRepository cardRepository) {
        this.repository = repository;
        this.emailSendler = emailSendler;
        this.cardRepository= cardRepository;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void runReminder() {
            List<Reminder> reminders = repository.findAllByRemindOnBeforeAndActive(LocalDateTime.now().withNano(0),true);
            reminders.forEach(reminder -> {
            emailSendler.setEmail(cardRepository.findCardByReminder(reminder).getCreatedBy());
            executorService.submit(emailSendler);
            reminder.setActive(false);
            repository.save(reminder);
            System.out.println("Time to finish!" + Objects.requireNonNull(reminder).getId());
        });
    }

}
