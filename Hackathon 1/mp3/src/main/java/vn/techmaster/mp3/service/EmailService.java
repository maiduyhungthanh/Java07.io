package vn.techmaster.mp3.service;

import lombok.AllArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender sender;

    public void sendEmail(String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Xác minh tài khoản");
        message.setText("Click vào link để xác minh tài khoản trên Mp3-App \nhttp://localhost:1993/validate/"+text);

        // Send Message!
        sender.send(message);
    }
}
