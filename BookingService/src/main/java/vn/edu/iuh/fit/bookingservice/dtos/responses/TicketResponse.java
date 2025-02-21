package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;
import vn.edu.iuh.fit.bookingservice.entities.Booking;
import vn.edu.iuh.fit.bookingservice.entities.TourSchedule;
import vn.edu.iuh.fit.bookingservice.enums.TicketType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TicketResponse {
    private double price;
    private String note;
    private int status;

    private String ticketType;
    private UUID bookingId;
    private UUID tourScheduleId;
    private String tourScheduleName;
    private String tourScheduleDesc;
    private LocalDateTime tourScheduleStartTime;
    private LocalDateTime tourScheduleEndTime;
}
