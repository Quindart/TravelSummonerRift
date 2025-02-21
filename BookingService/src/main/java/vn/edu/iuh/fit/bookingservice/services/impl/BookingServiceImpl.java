package vn.edu.iuh.fit.bookingservice.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponse;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.services.BookingService;

import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookingResponse> findBookingsByUserId(String userId) {
        String jpql = "SELECT b FROM Booking b WHERE b.userId = :userId";
        TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
        query.setParameter("userId", userId);
        List<Booking> bookings = query.getResultList();
        return bookings.stream()
                .map(booking -> new BookingResponse(
                       booking.getBookingId(),
                        booking.getStatus(),
                        booking.getTotalPrice(),
                        booking.getNote(),
                        booking.getUserFullName(),
                        booking.getUserPhone(),
                        booking.getUserEmail(),
                        booking.getUserAddress(),
                        booking.getUserId()
                ))
                .toList();
    }

}
