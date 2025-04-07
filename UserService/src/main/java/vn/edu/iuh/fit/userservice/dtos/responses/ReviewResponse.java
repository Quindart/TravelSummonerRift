package vn.edu.iuh.fit.userservice.dtos.responses;


import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}