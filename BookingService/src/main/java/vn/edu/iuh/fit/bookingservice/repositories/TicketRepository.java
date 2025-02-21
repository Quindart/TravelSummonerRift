package vn.edu.iuh.fit.bookingservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.bookingservice.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {}
