package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.bookingservice.entities.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query(value = """
            SELECT r.review_id, r.review_date, r.user_id, r.username, r.content, r.rating, r.tour_schedule_id,
                   STRING_AGG((CAST(rf.file_url AS TEXT) || 'Order' || CAST(rf.file_order AS TEXT)), ', ') AS files
            FROM reviews r
            JOIN review_files rf ON rf.review_id = r.review_id
            JOIN tour_schedules ts ON r.tour_schedule_id = ts.tour_schedule_id
            JOIN tours ON ts.tour_id = tours.tour_id
            WHERE ts.tour_id = :tourId
            GROUP BY r.review_id, r.review_date, r.user_id, r.username, r.content, r.rating, r.tour_schedule_id
            """, nativeQuery = true)
    List<Object[]> getReviewsByTourId(@Param("tourId") String tourId, Pageable pageable);

    @Query(value = """
            SELECT rating,count(rating)from tours t  join tour_schedules ts on t.tour_id = ts.tour_id
                                    join reviews r on r.tour_schedule_id = ts.tour_schedule_id
            WHERE t.tour_id = :tourId
            group by rating
        """,nativeQuery = true)
    List<Object[]> getTotalRatingOfTour(String tourId);
}
