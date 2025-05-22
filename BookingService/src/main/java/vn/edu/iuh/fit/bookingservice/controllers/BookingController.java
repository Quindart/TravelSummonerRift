package vn.edu.iuh.fit.bookingservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
import vn.edu.iuh.fit.bookingservice.exception.MessageResponse;
import vn.edu.iuh.fit.bookingservice.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookingController {

    @Autowired
    private BookingService bookingService;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-booking-admin")
    public MessageResponse<BookingResponseDTO> createBookingAdmin(@RequestBody BookingRequest request){
        BookingResponseDTO response = bookingService.createBookingForAdmin(request);
        return MessageResponse.<BookingResponseDTO>builder()
                .statusCode(200)
                .success(true)
                .data(response)
                .message("Dat tour thanh cong")
                .build();
    }

    @GetMapping("/my-bookings")
    public MessageResponse<List<BookingResponseDTO>> getBookingHistory(){
        return MessageResponse.<List<BookingResponseDTO>>builder()
                .statusCode(200)
                .success(true)
                .data(bookingService.getBookingHistory())
                .message("Lịch sử booking")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public MessageResponse<List<BookingResponseDTO>> getAllBookings(){
        return MessageResponse.<List<BookingResponseDTO>>builder()
                .statusCode(200)
                .success(true)
                .data(bookingService.getAllBookings())
                .message("Danh sách booking")
                .build();
    }


}
