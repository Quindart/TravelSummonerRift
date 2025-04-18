package vn.edu.iuh.fit.bookingservice.Infra.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.bookingservice.configs.AuthenticationRequestInterceptor;
import vn.edu.iuh.fit.bookingservice.dtos.responses.UserResponse;

@FeignClient(name = "user-service-client", contextId = "iUserClient", url = "${app.user-client-url}", configuration = {AuthenticationRequestInterceptor.class})
public interface IUserClient {



    @GetMapping("/users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") String id);
}

