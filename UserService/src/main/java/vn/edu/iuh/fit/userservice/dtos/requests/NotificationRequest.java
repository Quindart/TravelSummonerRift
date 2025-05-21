package vn.edu.iuh.fit.userservice.dtos.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    private String toEmail;
    private String data;
}
