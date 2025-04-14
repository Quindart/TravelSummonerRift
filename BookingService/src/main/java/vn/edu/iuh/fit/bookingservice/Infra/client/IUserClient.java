package vn.edu.iuh.fit.bookingservice.Infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;

@FeignClient(name = "user-service", url = "http://localhost:5723")
public interface IUserClient {

    @GetMapping("/users/{id}")
    MessageResponse<UserResponse> getUserById(@PathVariable("id") String id);
}

