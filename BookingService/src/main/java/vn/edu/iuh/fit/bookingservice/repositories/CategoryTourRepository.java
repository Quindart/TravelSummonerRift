package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.bookingservice.entities.CategoryTour;

public interface CategoryTourRepository extends JpaRepository<CategoryTour, String> {
}