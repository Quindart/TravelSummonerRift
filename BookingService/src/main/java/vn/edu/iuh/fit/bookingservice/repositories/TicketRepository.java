package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.Ticket;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
    long countByTourSchedule(TourSchedule tourSchedule);
    long countByBooking_BookingId(String bookingId);
    void deleteByBooking(Booking booking);
}
