package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.iuh.fit.bookingservice.dtos.TicketDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.Ticket;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingResponseDTO toBookingResponseDTO(Booking booking);

    List<TicketDTO> toTicketResponseDTOs(List<Ticket> tickets);

}