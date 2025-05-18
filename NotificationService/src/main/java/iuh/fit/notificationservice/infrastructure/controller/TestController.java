package iuh.fit.notificationservice.infrastructure.controller;

import iuh.fit.notificationservice.domain.entity.Notification;
import iuh.fit.notificationservice.domain.exception.MessageResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/djtmemay")
    public String test() {
        return "DJECONMEMAYLOMBOK";
    }

    @PostMapping("/djtmemay")
    public MessageResponse<Notification> hello(@RequestBody Notification data) {
        return MessageResponse.<Notification>builder().message("djtmemay").success(true).statusCode(200).data(
                Notification.builder().data(data.getData()).toEmail(data.getToEmail()).build()
        ).build();
    }
    @PutMapping("/djtmemay")
    public Notification hello2(@RequestBody Notification data) {
        return data;
    }
}
