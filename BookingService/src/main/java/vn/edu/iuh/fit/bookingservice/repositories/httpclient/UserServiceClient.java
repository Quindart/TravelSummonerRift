package vn.edu.iuh.fit.bookingservice.repositories.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.bookingservice.configs.AuthenticationRequestInterceptor;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;

@FeignClient(name = "user-service-client", contextId = "iUserClient", url = "${app.user-client-url}", configuration = {AuthenticationRequestInterceptor.class})
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    MessageResponse<UserResponse> getUserById(@PathVariable("id") String id);
}
