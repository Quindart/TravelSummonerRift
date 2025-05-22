package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, String> {
     List<TourSchedule> findTourScheduleByTour_TourId(String tourId);
    Optional<TourSchedule> findByTourScheduleId(String tourScheduleId);
    Optional<TourSchedule> findByTourScheduleIdAndIsActiveTrue(String tourScheduleId);

    @Transactional
    @Modifying
    @Query("update TourSchedule t set t.isActive = ?1 where t.tourScheduleId = ?2")
    int updateIsActiveByTourScheduleId(boolean isActive, String tourScheduleId);
}
