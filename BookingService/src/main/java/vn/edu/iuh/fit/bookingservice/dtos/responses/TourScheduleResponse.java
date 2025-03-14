package vn.edu.iuh.fit.bookingservice.dtos.responses;

import jakarta.persistence.*;
import lombok.Data;
import vn.edu.iuh.fit.bookingservice.entities.Tour;

import java.time.LocalDateTime;

@Data
public class TourScheduleResponse {
    String tourScheduleId;
    String name;
    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;
    double adultPrice;
    double childPrice;
    double babyPrice;
    int slot;
    String tourId;
}
