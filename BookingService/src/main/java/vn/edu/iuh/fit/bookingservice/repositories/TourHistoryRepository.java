package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TourHistoryResponse;
import vn.edu.iuh.fit.bookingservice.entities.TourHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourHistoryRepository extends JpaRepository<TourHistory, String> {
    List<TourHistory> findByUserIdOrderByViewDateDesc(String userId);

    Optional<TourHistory> findByUserIdAndTourTourId(String userId, String tourId);

    @Query("SELECT th FROM TourHistory th WHERE th.userId = :userId ORDER BY th.viewDate ASC")
    List<TourHistory> findOldestByUserId(@Param("userId") String userId);

}