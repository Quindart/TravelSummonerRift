package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;

public interface TourScheduleRepository extends JpaRepository<TourSchedule, String> {}
