package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourScheduleDetailResponse {
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double adultPrice;
    private double childPrice;
    private double babyPrice;
    private int slot;
}
