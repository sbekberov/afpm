package spd.trello.reminder;

import lombok.Data;
import org.springframework.stereotype.Component;
import spd.trello.service.EmailSenderService;

import java.util.concurrent.atomic.AtomicInteger;
@Component
@Data
public class EmailSendler implements Runnable {
    private String email;
    private AtomicInteger sendEmails = new AtomicInteger();
    private EmailSenderService emailSenderService;

    @Override
    public void run(){
        {
            emailSenderService.sendMail(email,"Reminder from Trello","Hello, you had to finish you job!");
            sendEmails.incrementAndGet();
        }
    }
}
