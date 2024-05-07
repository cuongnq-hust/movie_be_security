package security.example.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.example.security.dto.email.EmailResponse;
import security.example.security.service.EmailService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<EmailResponse>> getListEmail() {
        List<EmailResponse> emailResponseList = emailService.findListEmail();
        return new ResponseEntity<>(emailResponseList, HttpStatus.OK);
    }
    @GetMapping("/send-emails")
    public String sendEmailsToAll() {
        List<EmailResponse> emailList = emailService.findListEmail();
        String subject = "Your Subject Here";
        String text = "Your Email Body Here";
        emailService.sendEmailsToAll(emailList, subject, text);
        return "Emails sent successfully!";
    }
}
