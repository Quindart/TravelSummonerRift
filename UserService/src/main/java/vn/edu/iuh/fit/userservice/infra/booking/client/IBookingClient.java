package vn.edu.iuh.fit.userservice.infra.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.iuh.fit.userservice.infra.booking.dto.BookingResponse;

import java.util.List;

@FeignClient(name = "booking-service", url = "localhost:1902/booking-service/books")
public interface IBookingClient {
    @GetMapping("open-feign")
    public ResponseEntity<List<BookingResponse>> getBookingsOfUser() ;

}
