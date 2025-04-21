package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.util.Arrays;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, String>, JpaSpecificationExecutor<Tour> {

    List<Tour>  findByNameContainingIgnoreCase(String tourName);
    List<Tour> findToursByCategoryTour_CategoryTourId(String categoryTourId);
}
