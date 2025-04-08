package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private String reviewId;
    private LocalDateTime reviewDate;
    private String userId;
    private String username;
    private String content;
    private float rating;
    private List<String> imageUrls;
    private List<String> videoUrls;
    private String tourScheduleId;

    public void setTourScheduleResponse(TourScheduleResponse tourScheduleResponse) {
        if (tourScheduleResponse != null) {
            this.tourScheduleId = tourScheduleResponse.getTourScheduleId();
        }
    }
}