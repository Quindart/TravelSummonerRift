package vn.edu.iuh.fit.bookingservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;

import java.util.List;
import java.util.Optional;

public interface TourFavoriteRepository extends JpaRepository<TourFavorite, String> {
    List<TourFavorite> findByUserId(String userId);

    Optional<TourFavorite> findByUserIdAndTour_TourId(String userId, String tourTourId);
}
