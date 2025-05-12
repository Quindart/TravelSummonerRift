package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> getBookingsByUserId(String userId);
    List<Booking> findAllByStatus(BookingStatus status);

}
