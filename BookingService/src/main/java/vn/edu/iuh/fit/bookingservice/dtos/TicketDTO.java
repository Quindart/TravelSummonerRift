package vn.edu.iuh.fit.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.iuh.fit.bookingservice.enums.TicketType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {
    String ticketId;
    double price;
    int status;
    String note;
    TicketType ticketType;
}
