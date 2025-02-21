package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketRequest {
    private double price;
    private String note;
    private int status;

    private String ticketType;
    private String bookingId;
    private String tourScheduleName;
    private String tourScheduleDesc;
    private LocalDateTime tourScheduleStartTime;
    private LocalDateTime tourScheduleEndTime;
}
