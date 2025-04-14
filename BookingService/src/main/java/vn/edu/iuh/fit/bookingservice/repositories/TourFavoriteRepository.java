package vn.edu.iuh.fit.bookingservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.bookingservice.entities.TourFavorite;

public interface TourFavoriteRepository extends JpaRepository<Long, TourFavorite> {
}
