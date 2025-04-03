package vn.edu.iuh.fit.userservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tieuvy5723@gmail.com");
        message.setTo(email);
        message.setSubject("Reset Password - OTP Verification");
        message.setText("OPT của bạn là: " + otp + ".OTP này sẽ hết hạn trong 5 phút.");
        emailSender.send(message);
    }
}