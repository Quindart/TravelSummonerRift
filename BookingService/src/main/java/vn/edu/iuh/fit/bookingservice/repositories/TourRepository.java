package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.util.*;

public interface TourRepository extends JpaRepository<Tour, String>, JpaSpecificationExecutor<Tour> {

    List<Tour>  findByNameContainingIgnoreCase(String tourName);
    List<Tour> findToursByCategoryTour_CategoryTourId(String categoryTourId);
    Optional<Tour> findToursByTourId(String tourId);
    List<Tour> findByIsActiveTrue();
}
