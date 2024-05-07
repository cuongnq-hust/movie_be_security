package security.example.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import security.example.security.dto.email.EmailResponse;
import security.example.security.repository.EmailRepository;

import java.util.List;

@Service
public class EmailService {
    private final EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public List<EmailResponse> findListEmail() {
        return emailRepository.findListUser();
    }
    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    public void sendEmailsToAll(List<EmailResponse> emailList, String subject, String text) {
        for (EmailResponse emailResponse : emailList) {
            sendSimpleMessage(emailResponse.getEmail(), subject, text);
        }
    }
}
