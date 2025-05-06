package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourResponse;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTourRepository extends JpaRepository<CategoryTour, String> {
    Optional<CategoryTour> findByCategoryTourId(String id);
}
