package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RatingTotalResponse {
    private float average;
    private List<RatingResponse> listRating;
}
