package vn.edu.iuh.fit.bookingservice.services;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponse;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
import vn.edu.iuh.fit.bookingservice.entities.Booking;

import java.util.List;
import java.util.UUID;

public interface BookingService{
    BookingResponseDTO createBooking(BookingRequest request);
    List<BookingResponseDTO> getBookingHistory();
}
