package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.bookingservice.dtos.BookingCategoryStatDTO;
import vn.edu.iuh.fit.bookingservice.dtos.MonthlyRevenueDTO;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> getBookingsByUserId(String userId);
    List<Booking> findAllByStatus(BookingStatus status);

    Optional<Booking> findByUserIdAndTourSchedule_tourScheduleId(String userId, String tourScheduleId);

    @Query(value = """
        SELECT c.name AS categoryName, COUNT(b.booking_id) AS count
        FROM bookings b
        JOIN tour_schedules ts ON b.tour_schedule_id = ts.tour_schedule_id
        JOIN tours t ON ts.tour_id = t.tour_id
        JOIN category_tours c ON t.category_tour_id = c.category_tour_id
        WHERE b.status = 'PAID'
             AND EXTRACT(YEAR FROM b.created_at) = :year
        GROUP BY c.name
        ORDER BY count DESC
        """, nativeQuery = true)
    List<BookingCategoryStatDTO> countBookingsByCategoryInYear(@Param("year") int year);


    @Query(value = """
    SELECT 
        EXTRACT(MONTH FROM b.created_at) AS month,
        SUM(b.total_price) AS totalRevenue
        FROM bookings b
        WHERE b.status = 'PAID'
          AND EXTRACT(YEAR FROM b.created_at) = :year
        GROUP BY month
        ORDER BY month
        """, nativeQuery = true)
    List<MonthlyRevenueDTO> getMonthlyRevenueByYear(@Param("year") int year);

}
