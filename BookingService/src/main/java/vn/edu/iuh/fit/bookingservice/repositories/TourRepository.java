package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.bookingservice.dtos.TopTourByIdDTO;

import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.util.*;

public interface TourRepository extends JpaRepository<Tour, String>, JpaSpecificationExecutor<Tour> {

    List<Tour>  findByNameContainingIgnoreCase(String tourName);
    List<Tour> findToursByCategoryTour_CategoryTourId(String categoryTourId);
    Optional<Tour> findToursByTourId(String tourId);
    List<Tour> findByIsActiveTrue();

    @Query(value = "SELECT recipient_token FROM users WHERE is_active = true", nativeQuery = true)
    List<String> findAllRecipientTokensOfActiveUsers();


    @Query(value = """
    SELECT t.tour_id AS tourId, COUNT(b.booking_id) AS totalBooking
    FROM bookings b
    JOIN tour_schedules ts ON b.tour_schedule_id = ts.tour_schedule_id
    JOIN tours t ON ts.tour_id = t.tour_id
    WHERE b.status = 'PAID'
      AND EXTRACT(YEAR FROM b.created_at) = :year
    GROUP BY t.tour_id
    ORDER BY totalBooking DESC
    LIMIT 3
    """, nativeQuery = true)
    List<TopTourByIdDTO> findTop3TourIdsByYear(@Param("year") int year);

}
