package vn.edu.iuh.fit.bookingservice.repositories.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.bookingservice.configs.AuthenticationRequestInterceptor;
import vn.edu.iuh.fit.bookingservice.dtos.requests.NotificationRequest;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;

@FeignClient(name = "notification-service-client", contextId = "notificationClient", url = "http://localhost:2005/notification-service", configuration = {AuthenticationRequestInterceptor.class})
public interface MailServiceClient {
    @PostMapping("/sendMail/send-mail")
    MessageResponse<Boolean> sendMailOtp(@RequestBody()NotificationRequest notificationRequest);
}
