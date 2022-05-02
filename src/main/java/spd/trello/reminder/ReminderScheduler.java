package spd.trello.reminder;


import lombok.extern.log4j.Log4j2;
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
@Log4j2
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


    @Scheduled(cron = "${cron.expression}")
    public void runReminder() {
            List<Reminder> reminders = repository.findAllByRemindOnBeforeAndActive(LocalDateTime.now().withNano(0),true);
            reminders.forEach(reminder -> {
            emailSendler.setEmail(cardRepository.findCardByReminder(reminder).getCreatedBy());
            executorService.submit(emailSendler);
            log.info("Email sent to {} ", cardRepository.findCardByReminder(reminder).getCreatedBy());
            reminder.setActive(false);
            repository.save(reminder);
            log.info("Time to finish!" + Objects.requireNonNull(reminder).getId());
        });
    }

}
