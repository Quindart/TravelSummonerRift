package vn.edu.iuh.fit.bookingservice.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.bookingservice.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    @JsonProperty("userId")
    String userId;

    @JsonProperty("email")
    String email;

    @JsonProperty("phone")
    String phone;

    @JsonProperty("username")
    String username;

    @JsonProperty("fullName")
    String fullName;

    @JsonProperty("role")
    Role role;
}
