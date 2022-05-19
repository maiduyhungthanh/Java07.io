package vn.techmaster.user.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender sender;

    public void sendEmail(String email, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(subject);
        message.setText("Mật khẩu truy cập của bạn la : "+text);

        // Send Message!
        sender.send(message);


    }
}
