package iuh.fit.notificationservice.application.dto.request.notification;

import lombok.*;
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
