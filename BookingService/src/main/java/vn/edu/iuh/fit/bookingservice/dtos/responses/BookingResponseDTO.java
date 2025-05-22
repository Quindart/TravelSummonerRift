package vn.edu.iuh.fit.bookingservice.dtos.responses;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.iuh.fit.bookingservice.dtos.TicketDTO;
import vn.edu.iuh.fit.bookingservice.dtos.TourScheduleDTO;
import vn.edu.iuh.fit.bookingservice.enums.BookingStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    String bookingId;
    BookingStatus status;
    double totalPrice;
    String note;
    String userFullName;
    String userPhone;
    String userEmail;
    String userAddress;

    TourScheduleDTO tourSchedule;
    List<TicketDTO> tickets;

}