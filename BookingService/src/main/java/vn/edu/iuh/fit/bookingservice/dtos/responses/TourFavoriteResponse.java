package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourFavoriteResponse {
    private String tourFavoriteId;
    private TourOverviewResponse tour;
}
