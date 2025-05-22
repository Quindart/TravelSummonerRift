package iuh.fit.notificationservice.infrastructure.controller;

import iuh.fit.notificationservice.application.dto.request.notification.NotificationRequest;
import iuh.fit.notificationservice.application.dto.request.user.SendOtp;
import iuh.fit.notificationservice.application.service.FirebaseService;
import iuh.fit.notificationservice.application.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sendMail")
@RequiredArgsConstructor
public class NotificationController {
    private final MailService mailService;
    private final FirebaseService firebaseService;

    @PostMapping(value = "/send-mail")
    public void sendMail(@RequestBody() SendOtp data){
        this.mailService.sendUserConfirmation(data.getToEmail(),data.getOtp());
    }

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody NotificationRequest data) {
        if (data.getData() == null) {
            data.setData(new HashMap<>());
        }
        data.getData().put("title", data.getTitle());
        data.getData().put("body", data.getBody());

        firebaseService.sendNotificationFirebase(data);
    }
}
