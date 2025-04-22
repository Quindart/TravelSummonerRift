package vn.edu.iuh.fit.userservice.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.edu.iuh.fit.userservice.configs.AuthenticationRequestInterceptor;

import java.util.List;

@FeignClient(name = "booking-service", url = "localhost:1902/booking-service/books", configuration = {AuthenticationRequestInterceptor.class})
public interface IBookingClient {
    @GetMapping("open-feign")
    public ResponseEntity<List<vn.edu.iuh.fit.userservice.infra.dto.BookingResponse>> getBookingsOfUser(
    );

}
