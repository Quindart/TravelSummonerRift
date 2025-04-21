package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourFavoriteRequest {
    private String userId;
    private String tourId;
}

