package vn.edu.iuh.fit.bookingservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;
import vn.edu.iuh.fit.bookingservice.repositories.BookingRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TicketRepository;
import vn.edu.iuh.fit.bookingservice.repositories.TourScheduleRepository;

@Service
@RequiredArgsConstructor
public class BookingCleanupService {
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final TourScheduleRepository tourScheduleRepository;

    @Transactional
    public void handleFailedPayment(String txnRef) {
        Booking booking = bookingRepository.findById(txnRef)
                .orElseThrow(() -> new NotFoundException("Booking không tồn tại!"));

        booking.setStatus(BookingStatus.EXPIRED);
        bookingRepository.save(booking);

        TourSchedule tourSchedule = booking.getTourSchedule();
        long bookedTicketsCount = ticketRepository.countByBooking_BookingId(booking.getBookingId());
        tourSchedule.setSlot((int) (tourSchedule.getSlot() + bookedTicketsCount));
        tourScheduleRepository.save(tourSchedule);

        ticketRepository.deleteByBooking_BookingId(booking.getBookingId());
    }


}