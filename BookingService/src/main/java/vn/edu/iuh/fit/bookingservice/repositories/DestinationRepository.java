package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.iuh.fit.bookingservice.entities.Destination;

public interface DestinationRepository extends JpaRepository<Destination, String> {}
