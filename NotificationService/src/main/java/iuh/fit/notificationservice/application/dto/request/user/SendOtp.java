package iuh.fit.notificationservice.application.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendOtp {
    String toEmail;
    String otp;
}
