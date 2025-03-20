package vn.edu.iuh.fit.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.iuh.fit.bookingservice.dtos.TicketDTO;
import vn.edu.iuh.fit.bookingservice.dtos.requests.BookingRequest;
import vn.edu.iuh.fit.bookingservice.dtos.responses.BookingResponseDTO;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.Ticket;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TicketMapper.class, TourScheduleMapper.class})
public interface BookingMapper {
    @Mapping(source = "booking.tourSchedule", target = "tourSchedule")
    @Mapping(source = "tickets", target = "tickets")
    BookingResponseDTO toBookingResponseDTO(Booking booking, List<Ticket> tickets);

    List<TicketDTO> toTicketResponseDTOs(List<Ticket> tickets);
}