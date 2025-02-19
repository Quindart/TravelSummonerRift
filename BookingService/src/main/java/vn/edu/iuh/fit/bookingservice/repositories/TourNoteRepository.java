package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.TourNote;

import java.util.Collection;
import java.util.List;

public interface TourNoteRepository extends JpaRepository<TourNote, String> {

}
