package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.bookingservice.entities.TourNote;

public interface TourNoteRepository extends JpaRepository<TourNote, String> {
}