package vn.edu.iuh.fit.userservice.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.userservice.configs.AuthenticationRequestInterceptor;
import vn.edu.iuh.fit.userservice.dtos.requests.NotificationRequest;
import vn.edu.iuh.fit.userservice.dtos.requests.SendOtp;
import vn.edu.iuh.fit.userservice.exception.MessageResponse;

@FeignClient(name = "notification-service-client", contextId = "notificationClient", url = "http://localhost:2005/notification-service", configuration = {AuthenticationRequestInterceptor.class})
public interface MailServiceClient {
    @PostMapping("/sendMail/send-mail")
    MessageResponse<Boolean> sendMailOtp(@RequestBody() SendOtp sendOtp);
}
