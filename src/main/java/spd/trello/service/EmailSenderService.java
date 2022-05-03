package spd.trello.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender emailSender;

    public EmailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public  void sendMail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("s.bekberov@gmail.com");
        message.setTo("bekberovselim@gmail.com");
        message.setText(body);
        message.setSubject(subject);

        emailSender.send(message);


    }
}