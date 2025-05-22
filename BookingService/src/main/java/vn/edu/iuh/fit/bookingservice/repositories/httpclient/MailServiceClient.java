package vn.edu.iuh.fit.bookingservice.repositories.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.bookingservice.configs.AuthenticationRequestInterceptor;
import vn.edu.iuh.fit.bookingservice.dtos.requests.NotificationRequest;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;

@FeignClient(name = "notification-service-client", contextId = "notificationClient", url = "https://travelsummonerrift.me/api/v1/notification-service", configuration = {AuthenticationRequestInterceptor.class})
public interface MailServiceClient {
    @PostMapping("/sendMail/send-notification")
    MessageResponse<Boolean> sendNotification(@RequestBody() NotificationRequest notificationRequest);
}
