package vn.edu.iuh.fit.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourScheduleDTO {
    String tourScheduleId;
    String name;
    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
