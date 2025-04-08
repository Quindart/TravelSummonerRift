package vn.edu.iuh.fit.userservice.dtos.requests;

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
public class ReviewRequest {
    private LocalDateTime reviewDate;
    private String userId;
    private String content;
    private String username;
    private List<String> imageUrls;
    private List<String> videoUrls;
    private String tourScheduleId;
}