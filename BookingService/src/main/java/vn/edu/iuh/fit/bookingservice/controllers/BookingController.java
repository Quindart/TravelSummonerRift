package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponse;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.exception.SuccessEntityResponse;
import vn.edu.iuh.fit.bookingservice.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("demo")
    public String testRouting(){
        return "book-router";
    }
    @GetMapping("/open-feign")
    public List<BookingResponse> getBookingsByUserId() {
        return bookingService.findBookingsByUserId("8f68b683-d5ef-4ede-88fc-37e6e56ef9b1").stream().toList();

    }
}
