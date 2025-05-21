package vn.edu.iuh.fit.userservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.userservice.dtos.requests.NotificationRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.SendOtp;
import vn.edu.iuh.fit.userservice.infra.client.MailServiceClient;

@Service
@Slf4j
public class EmailService {
   @Autowired
   private MailServiceClient mailServiceClient;
    public void sendOtpEmail(String email, String otp) {
         mailServiceClient.sendMailOtp(SendOtp.builder().toEmail(email).otp(otp).build());
    }
}