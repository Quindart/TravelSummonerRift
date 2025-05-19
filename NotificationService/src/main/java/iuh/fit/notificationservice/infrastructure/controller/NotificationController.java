package iuh.fit.notificationservice.infrastructure.controller;

import iuh.fit.notificationservice.application.dto.request.notification.NotificationRequest;
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
    private  final Logger logger = (Logger) LoggerFactory.getLogger(NotificationController.class);
    @PostMapping(value = "/send-mail")
    public MessageResponse<Notification> sendMail(@RequestBody() NotificationRequest data){

        this.mailService.sendUserConfirmation("hieu","123");
        return MessageResponse.<Notification>builder().message("Gửi thành công").success(true).statusCode(200).data(Notification.builder()
                        .toEmail(data.getToEmail())
                        .data(data.getData())
                .build()).build();
    }


}
