package iuh.fit.notificationservice.controller;

import iuh.fit.notificationservice.domain.entity.Notification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationController {
    @GetMapping("/djtmemay")
    public String hello(){
        return "DJTMEMAY SPRING";
    }
    @PostMapping(value = "/djtmemay")
    public Notification hello(@RequestBody Notification data){
        return data;
    }
}
