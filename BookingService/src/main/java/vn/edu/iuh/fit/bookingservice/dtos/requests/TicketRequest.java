package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.Data;
import vn.edu.iuh.fit.bookingservice.enums.TicketType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TicketRequest {
    private String fullName;
    private String gender;
    private LocalDate birthDate;
    private TicketType ticketType;
}
