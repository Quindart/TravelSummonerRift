package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.bookingservice.entities.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> getBookingsByUserId(String userId);

    Optional<Booking> findByUserIdAndTourSchedule_tourScheduleId(String userId, String tourScheduleId);
}
