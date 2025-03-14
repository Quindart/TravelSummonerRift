package vn.edu.iuh.fit.bookingservice.services;

import vn.edu.iuh.fit.bookingservice.dtos.requests.TicketRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.TicketResponse;

import java.util.List;

public interface TicketService {

    //TODO:[GET]
    List<TicketResponse> getTickets();
    TicketResponse getTicketById(String ticketId);

    //TODO:[POST]
    TicketResponse createTicket(TicketRequest ticketRequest);

    //TODO:[PUT]
    TicketResponse updateTicket(String ticketId,TicketRequest ticketRequest);

    //TODO:[DELETE]
    void deleteTicket(String ticketId);
}
