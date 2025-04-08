package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.bookingservice.entities.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("SELECT r FROM Review r WHERE r.tourSchedule.tourScheduleId = :tourScheduleId")
    List<Review> findByTourScheduleId(@Param("tourScheduleId") String tourScheduleId);
}
