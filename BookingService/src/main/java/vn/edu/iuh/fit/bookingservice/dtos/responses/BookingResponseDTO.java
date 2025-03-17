package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.iuh.fit.bookingservice.dtos.TicketDTO;
import vn.edu.iuh.fit.bookingservice.dtos.TourScheduleDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    String bookingId;
    int status;
    double totalPrice;
    String note;
    String userFullName;
    String userPhone;
    String userEmail;
    String userAddress;

    TourScheduleDTO tourSchedule;
    List<TicketDTO> tickets;
}