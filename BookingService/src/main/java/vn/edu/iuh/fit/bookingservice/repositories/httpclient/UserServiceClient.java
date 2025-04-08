package vn.edu.iuh.fit.bookingservice.repositories.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.bookingservice.dtos.requests.IntrospectRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.IntrospectResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:5723/user-service")
public interface UserServiceClient {
//    @PostMapping(value = "/auth/introspect", consumes = MediaType.APPLICATION_JSON_VALUE)
//    MessageResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request);
//
    @GetMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<UserResponse> getAllUsers();
}
