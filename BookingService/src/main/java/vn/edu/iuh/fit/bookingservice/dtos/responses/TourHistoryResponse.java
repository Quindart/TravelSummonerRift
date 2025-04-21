package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourHistoryResponse {
    private String tourHistoryId;
    private TourOverviewResponse tour;
    private LocalDateTime viewDate;
}
