package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
