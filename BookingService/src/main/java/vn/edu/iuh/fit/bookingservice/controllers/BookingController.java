package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
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
        return bookingService.findBookingsByUserId("34ba8507-2cea-411f-84dc-9aa236ae804e").stream().toList();
    }

    @PostMapping("/create-booking")
    public MessageResponse<BookingResponseDTO> createBooking (@RequestBody BookingRequest request){
        BookingResponseDTO response = bookingService.createBooking(request);
        return MessageResponse.<BookingResponseDTO>builder()
                .statusCode(200)
                .success(true)
                .data(response)
                .message("Dat tour thanh cong")
                .build();
    }


}
