package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.iuh.fit.bookingservice.dtos.TicketDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    String userFullName;
    String userPhone;
    String userEmail;
    String userAddress;
    String userId;
    String tourScheduleId;
    String note;

    List<TicketDTO> tickets;
}