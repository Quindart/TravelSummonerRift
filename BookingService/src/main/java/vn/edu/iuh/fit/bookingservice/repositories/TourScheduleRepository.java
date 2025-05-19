package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

import java.util.List;

public interface TourScheduleRepository extends JpaRepository<TourSchedule, String> {
    public List<TourSchedule> findTourScheduleByTour_TourId(String tourId);

}
