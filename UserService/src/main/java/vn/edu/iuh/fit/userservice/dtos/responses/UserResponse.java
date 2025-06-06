package vn.edu.iuh.fit.userservice.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.userservice.enums.Role;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    @JsonProperty("userId")
    String userId;
    String email;
    String phone;
    String username;
    String fullName;
    String avatarUrl;
    Date birthday;
    int gender;

    Role role;
}
