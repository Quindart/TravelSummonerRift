package vn.edu.iuh.fit.bookingservice.dtos.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationRequest {
    String recipientToken;
    String title;
    String body;
    String image;
    Map<String,String> data;
}
