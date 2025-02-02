package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.TourDestination;

public interface TourDestinationRepository extends JpaRepository<TourDestination, String> {}
