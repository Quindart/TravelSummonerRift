package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.TourDestination;

import java.util.List;

public interface TourDestinationRepository extends JpaRepository<TourDestination, String> {
    List<TourDestination> findByTour_TourId(String tourId);
}
