package iuh.fit.notificationservice.infrastructure.controller;

import iuh.fit.notificationservice.application.dto.request.notification.NotificationRequest;
import iuh.fit.notificationservice.application.dto.request.user.SendOtp;
import iuh.fit.notificationservice.application.service.MailService;
import iuh.fit.notificationservice.domain.entity.Notification;
import iuh.fit.notificationservice.domain.exception.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/sendMail")
@RequiredArgsConstructor
public class NotificationController {
    private final MailService mailService;
    @PostMapping(value = "/send-mail")
    public void sendMail(@RequestBody() SendOtp data){
        this.mailService.sendUserConfirmation(data.getToEmail(),data.getOtp());
    }
}
