package vn.edu.iuh.fit.bookingservice.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.bookingservice.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    String userId;
    String email;
    String phone;
    String username;
    String fullName;
    Role role;
}
