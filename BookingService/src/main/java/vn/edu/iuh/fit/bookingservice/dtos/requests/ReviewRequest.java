package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ReviewRequest {
    private String content;
    private String tourScheduleId;
    private float rating;

    private String listFiles = null;
}