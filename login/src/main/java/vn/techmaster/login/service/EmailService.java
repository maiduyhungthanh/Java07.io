package vn.techmaster.login.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
     private JavaMailSender sender;

    public void sendEmail(String email, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your verification code of my application");
        message.setText("http://localhost:9090/validate/"+text);

        // Send Message!
        sender.send(message);


    }
}