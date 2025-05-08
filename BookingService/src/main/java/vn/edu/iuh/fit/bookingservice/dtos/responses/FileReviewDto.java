package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileReviewDto {
    String file_url;
    int file_order;
}
