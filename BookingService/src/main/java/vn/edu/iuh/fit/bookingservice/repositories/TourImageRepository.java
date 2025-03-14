package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.TourImage;

import java.util.List;

public interface TourImageRepository extends JpaRepository<TourImage, String> {
    List<TourImage> findByTour_TourId(String tourId);
}
